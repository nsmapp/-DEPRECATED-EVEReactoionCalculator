package by.nepravsky.sm.domain.usecase.reactor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Observable<ReactionPres> get(final String reactionName, final int regionId, final int runs){

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
                        List<Formula> formulasList = new ArrayList<>();
                        List<Formula> subFormulaList = new ArrayList<>();

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

                                        Formula subProductFormula = formulaMap.get(material.getTypeID());
                                        double quantity = material.getQuantity() * run;
                                        double formulaQuantity = subProductFormula.getProduct().getQuantity();
                                        int subRun = (int) Math.ceil(quantity / formulaQuantity);
                                        subProductFormula.getProduct().initRuns(subRun);
                                        subFormulaList.add(subProductFormula);
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
                        Observable reactionTimeObservable = Observable.just(reactionTime);

                        final List<String> productIdList = new ArrayList<>();
                        List<String> materialIdList = new ArrayList<>();
                        productIdList.add(String.valueOf(formula.getProduct().getTypeID()));
                        materialIdList = reactionUtils.getIdList(materialList);

                        Observable<Map<Integer, ItemInfo>> productMapObservable = getItemMap
                                .getAsync(productIdList);
                        Observable<Map<Integer, ItemInfo>> materialMapObservable = getItemMap
                                .getAsync(materialIdList);

                        final Observable<Map<Integer, ItemPriceInfo>> productPriceObservable
                                = getItemPriceMap.getAsync(productIdList, regionId);
                        Observable<Map<Integer, ItemPriceInfo>> materialPriceObservable
                                = getItemPriceMap.getAsync(materialIdList, regionId);


                        Observable zip = Observable.zip(
                                productObservable,
                                materialListObservable,
                                reactionTimeObservable,
                                productMapObservable,
                                materialMapObservable,
                                productPriceObservable,
                                materialPriceObservable,
                                new Function7<
                                        ReactionItem,
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
                                                              Integer reactionTime,
                                                              Map<Integer, ItemInfo> productInfo,
                                                              Map<Integer, ItemInfo> materialInfo,
                                                              Map<Integer, ItemPriceInfo> productPrice,
                                                              Map<Integer, ItemPriceInfo> materialPrice) throws Exception {



                                        ItemPres productPres = new ItemPres(
                                                product.getTypeID(),
                                                formula.getName(),
                                                productInfo.get(product.getTypeID()).getVolume(),
                                                product.getQuantity(),
                                                productPrice.get(product.getTypeID()).getSell(),
                                                productPrice.get(product.getTypeID()).getBuy()

                                        );

                                        List<ItemPres> materialListPres = reactionUtils.makeItemPres(
                                                materialList,
                                                materialInfo,
                                                materialPrice);


                                        return reactionUtils.makeReactionPres(
                                                reactionTime,
                                                productPres,
                                                materialListPres);

                                    }
                                }
                        );
                        return zip;

                    }
                });
    }







}
