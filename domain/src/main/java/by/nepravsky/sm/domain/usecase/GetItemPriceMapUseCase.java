package by.nepravsky.sm.domain.usecase;

import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import by.nepravsky.sm.domain.entity.ItemPriceInfo;
import by.nepravsky.sm.domain.executors.PostExecutionThread;
import by.nepravsky.sm.domain.repositories.PriceInfoRepository;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class GetItemPriceMapUseCase extends BaseUseCase{

    private PriceInfoRepository priceInfoRepository;

    @Inject
    public GetItemPriceMapUseCase(PostExecutionThread postExecutionThread, PriceInfoRepository priceInfoRepository) {
        super(postExecutionThread);
        this.priceInfoRepository = priceInfoRepository;
    }

    public Observable<Map<Integer, ItemPriceInfo>> getAsync(final List<Integer> typeIdList, int regionId){
        return priceInfoRepository.getPriceInfoList(typeIdList, regionId)
                .subscribeOn(executionThread)
                .observeOn(postExecutionThread)
                .map(new Function<List<ItemPriceInfo>, Map<Integer, ItemPriceInfo>>() {
                    @Override
                    public Map<Integer, ItemPriceInfo> apply(List<ItemPriceInfo> itemPriceList) throws Exception {

                        Map<Integer, ItemPriceInfo> priceInfoMap = new HashMap<>();
                        for (ItemPriceInfo priceInfo : itemPriceList){
                            priceInfoMap.put(priceInfo.getTypeId(), priceInfo);
                        }

                        if (priceInfoMap.size() != typeIdList.size()){
                            for (int id : typeIdList){
                                if (!priceInfoMap.containsKey(id)) {
                                    ItemPriceInfo itemPriceInfo = new ItemPriceInfo(0,0, id);
                                    priceInfoMap.put(id, itemPriceInfo);
                                }
                            }
                        }
                        return priceInfoMap;
                    }
                })
                .toObservable();
    }
}
