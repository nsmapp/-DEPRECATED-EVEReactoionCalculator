package by.nepravsky.sm.domain.usecase.reactor;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import by.nepravsky.sm.domain.entity.Formula;
import by.nepravsky.sm.domain.entity.ItemInfo;
import by.nepravsky.sm.domain.entity.ItemPriceInfo;
import by.nepravsky.sm.domain.entity.ReactionItem;
import by.nepravsky.sm.domain.entity.presentation.ItemPres;
import by.nepravsky.sm.domain.entity.presentation.ReactionPres;
import by.nepravsky.sm.domain.executors.PostExecutionThread;
import by.nepravsky.sm.domain.usecase.BaseUseCase;
import by.nepravsky.sm.domain.usecase.GetFormulaUseCase;
import by.nepravsky.sm.domain.usecase.GetItemMapUseCase;
import by.nepravsky.sm.domain.usecase.GetItemPriceMapUseCase;
import by.nepravsky.sm.domain.utils.ReactionUtils;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function7;
import io.reactivex.functions.Function8;

public class FullReactionUseCase extends BaseUseCase {

    private GetFormulaUseCase getFormula;
    private GetItemMapUseCase getItemMap;
    private GetItemPriceMapUseCase getItemPriceMap;

    @Inject
    public ReactionUtils reactionUtils;


    @Inject
    public FullReactionUseCase(PostExecutionThread postExecutionThread, GetFormulaUseCase getFormula, GetItemMapUseCase getItemMap, GetItemPriceMapUseCase getItemPriceMap) {
        super(postExecutionThread);
        this.getFormula = getFormula;
        this.getItemMap = getItemMap;
        this.getItemPriceMap = getItemPriceMap;

    }

    public Observable<ReactionPres> get(final String reactionName,
                                        final int regionId,
                                        final int runs,
                                        final double reactionTax){

        return getFormula.getAll()
                .subscribeOn(executionThread)
                .observeOn(postExecutionThread)
                .map(new Function<List<Formula>, Map<Integer, Formula>>() {
                    @Override
                    public Map<Integer, Formula> apply(List<Formula> formulaList) throws Exception {
                        Map<Integer, Formula> formulaMap = new HashMap<>();
                        for (Formula formula : formulaList){
                            formulaMap.put(formula.getId(), formula);
                        }
                        return formulaMap;
                    }
                })
                .flatMap(new Function<Map<Integer, Formula>, Observable<ReactionPres>>() {
                    @Override
                    public Observable<ReactionPres> apply(Map<Integer, Formula> formulaMap) throws Exception {

                        final Formula formula = reactionUtils.getReactionWithName(reactionName, formulaMap);

                        int reactionTime = 0;
                        List<ReactionItem> materialList = new ArrayList<>();
                        List<ReactionItem> subProductList = new ArrayList<>();

                        List<Formula> subFormulaList = new ArrayList<>();
                        List<Formula> formulasList = new ArrayList<>();

                        formula.getProduct().initRuns(runs);
                        formulasList.add(formula);

                        do {

                            for(Formula subFormula: formulasList){
                                double baseProductQuantity  = formulaMap.get(subFormula.getId()).getProduct().getReactionQuantity();
                                double productQuantity = subFormula.getProduct().getQuantity();
                                int run = (int) Math.ceil(productQuantity / baseProductQuantity);
                                reactionTime = reactionTime + subFormula.getTime() * run;

                                for (ReactionItem material: subFormula.getMaterial()){

                                    if (reactionUtils.isSubProduct(material, formulaMap)){

                                        Formula subProductFormula = formulaMap.get(material.getId());
                                        double quantity = material.getQuantity() * run;
                                        double formulaQuantity = subProductFormula.getProduct().getQuantity();
                                        int subRun = (int) Math.ceil(quantity / formulaQuantity);

                                        subProductFormula.getProduct().initRuns(subRun);
                                        subFormulaList.add(subProductFormula);
                                        subProductList.add(material);
                                    }else {
                                        material.initRuns(run);
                                        materialList.add(material);
                                    }
                                }

                            }

                            formulasList.clear();
                            formulasList.addAll(subFormulaList);
                            subFormulaList.clear();
                        }while (!formulasList.isEmpty());


                        Observable productObservable = Observable.just(formula.getProduct());
                        Observable materialListObservable = Observable.just(materialList);
                        Observable subProductListObservable = Observable.just(subProductList);
                        Observable reactionTimeObservable = Observable.just(reactionTime);

                        List<Integer> productIdList = new ArrayList<>();
                        productIdList.add(formula.getProduct().getId());
                        productIdList.addAll(reactionUtils.getIdList(subProductList));
                        List<Integer> materialIdList = reactionUtils.getIdList(materialList);


                        Observable<Map<Integer, ItemInfo>> productMapObservable = getItemMap
                                .getAsync(productIdList);
                        Observable<Map<Integer, ItemInfo>> materialMapObservable = getItemMap
                                .getAsync(materialIdList);

                        Observable<Map<Integer, ItemPriceInfo>> productPriceObservable
                                = getItemPriceMap.getAsync(productIdList, regionId);
                        Observable<Map<Integer, ItemPriceInfo>> materialPriceObservable
                                = getItemPriceMap.getAsync(materialIdList, regionId);


                        Observable zip = Observable.zip(
                                productObservable,
                                materialListObservable,
                                subProductListObservable,
                                reactionTimeObservable,
                                productMapObservable,
                                materialMapObservable,
                                productPriceObservable,
                                materialPriceObservable,
                                new Function8<
                                        ReactionItem,
                                        List<ReactionItem>,
                                        List<ReactionItem>,
                                        Integer,
                                        Map<Integer, ItemInfo>,
                                        Map<Integer, ItemInfo>,
                                        Map<Integer, ItemPriceInfo>,
                                        Map<Integer, ItemPriceInfo>,
                                        ReactionPres>() {
                                    @Override
                                    public ReactionPres apply(ReactionItem product,
                                                              List<ReactionItem> materialList,
                                                              List<ReactionItem> subProductList,
                                                              Integer reactionTime,
                                                              Map<Integer, ItemInfo> productInfo,
                                                              Map<Integer, ItemInfo> materialInfo,
                                                              Map<Integer, ItemPriceInfo> productPrice,
                                                              Map<Integer, ItemPriceInfo> materialPrice) throws Exception {



                                        ItemPres productPres = new ItemPres(
                                                product.getId(),
                                                productInfo.get(product.getId()).getName(),
                                                productInfo.get(product.getId()).getVolume(),
                                                product.getQuantity(),
                                                productPrice.get(product.getId()).getSell(),
                                                productPrice.get(product.getId()).getBuy(),
                                                productInfo.get(product.getId()).getBasePrice()

                                        );

                                        List<ItemPres> materialListPres = reactionUtils.makeItemPres(
                                                materialList,
                                                materialInfo,
                                                materialPrice
                                        );
                                        subProductList.add(product);
                                        List<ItemPres> subProductListPres = reactionUtils.makeItemPres(
                                                subProductList,
                                                productInfo,
                                                productPrice
                                        );

                                        materialListPres = reactionUtils.colapseItemPres(materialListPres);
                                        Collections.sort(materialListPres);

                                        return reactionUtils.makeReactionPres(
                                                reactionTime,
                                                productPres,
                                                materialListPres,
                                                subProductListPres,
                                                reactionTax
                                        );

                                    }
                                }
                        );
                        return zip;

                    }
                });
    }







}
