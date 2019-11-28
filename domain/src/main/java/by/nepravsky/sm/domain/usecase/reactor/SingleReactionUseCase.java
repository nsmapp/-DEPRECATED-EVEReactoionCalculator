package by.nepravsky.sm.domain.usecase.reactor;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
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
import io.reactivex.functions.Function5;

public class SingleReactionUseCase extends BaseUseCase {

    private GetFormulaUseCase getFormula;
    private GetItemMapUseCase getItemMap;
    private GetItemPriceMapUseCase getItemPriceMap;

    @Inject
    public ReactionUtils reactionUtils;

    @Inject
    public SingleReactionUseCase(PostExecutionThread postExecutionThread,
                                 GetFormulaUseCase getFormula,
                                 GetItemMapUseCase getItemMap,
                                 GetItemPriceMapUseCase getItemPriceMap) {
        super(postExecutionThread);
        this.getFormula = getFormula;
        this.getItemMap = getItemMap;
        this.getItemPriceMap = getItemPriceMap;
    }

    public Observable<ReactionPres> get(String reactionName,
                                        final int regionId,
                                        final int runs,
                                        final double reactionTax){

        return getFormula.getAsync(reactionName)
                .subscribeOn(executionThread)
                .observeOn(postExecutionThread)
                .flatMap(new Function<Formula, Observable<ReactionPres>>() {
                    @Override
                    public Observable<ReactionPres> apply(final Formula formula) throws Exception {

                        List<Integer> productIdList = new ArrayList<>();
                        productIdList.add(formula.getProduct().getId());
                        List<Integer> materialIdList = reactionUtils.getIdList(formula.getMaterial());

                        Observable productObservable = Observable.just(formula.getProduct());
                        Observable<Map<Integer, ItemInfo>> productMap = getItemMap
                                .getAsync(productIdList);
                        Observable<Map<Integer, ItemInfo>> materialMap = getItemMap
                                .getAsync(materialIdList);


                        Observable<Map<Integer, ItemPriceInfo>> productPrice = getItemPriceMap
                                .getAsync(productIdList, regionId);
                        Observable<Map<Integer, ItemPriceInfo>> materialPrice = getItemPriceMap
                                .getAsync(materialIdList, regionId);

                        Observable zip = Observable.zip( productObservable, productMap, materialMap, productPrice, materialPrice,
                                new Function5<
                                        ReactionItem,
                                        Map<Integer, ItemInfo>,
                                        Map<Integer, ItemInfo>,
                                        Map<Integer, ItemPriceInfo>,
                                        Map<Integer, ItemPriceInfo>,
                                        ReactionPres>() {
                                    @Override
                                    public ReactionPres apply(
                                            ReactionItem product,
                                            Map<Integer, ItemInfo> productInfoMap,
                                            Map<Integer, ItemInfo> materialInfoMap,
                                            Map<Integer, ItemPriceInfo> productPriceMap,
                                            Map<Integer, ItemPriceInfo> materialPriceMap) throws Exception {

                                        product.initRuns(runs);
                                        int id = product.getId();
                                        ItemPres productPres = new ItemPres(
                                                product.getId(),
                                                productInfoMap.get(id).getName(),
                                                productInfoMap.get(id).getVolume(),
                                                product.getReactionQuantity(),
                                                productPriceMap.get(id).getSell(),
                                                productPriceMap.get(id).getBuy(),
                                                productInfoMap.get(id).getBasePrice()

                                        );

                                        List<ItemPres> productPresList = new ArrayList<>();
                                        productPresList.add(productPres);
                                        List<ItemPres> materialsPresList = reactionUtils.makeItemPres(
                                                formula.getMaterial(),
                                                materialInfoMap,
                                                materialPriceMap
                                        );
                                        Collections.sort(materialsPresList);

                                        return reactionUtils.makeReactionPres(
                                                formula.getTime() * runs,
                                                productPres,
                                                materialsPresList,
                                                productPresList,
                                                reactionTax
                                        );
                                    }
                                });

                        return zip;
                    }
                });
    }



}
