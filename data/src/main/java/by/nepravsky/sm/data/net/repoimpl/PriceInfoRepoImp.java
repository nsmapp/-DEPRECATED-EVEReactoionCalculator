package by.nepravsky.sm.data.net.repoimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import by.nepravsky.sm.data.net.RestModule;
import by.nepravsky.sm.data.net.entity.MarketOrder;
import by.nepravsky.sm.domain.entity.ItemPriceInfo;
import by.nepravsky.sm.domain.repositories.PriceInfoRepository;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public class PriceInfoRepoImp implements PriceInfoRepository {


    private RestModule restModule;

    @Inject
    public PriceInfoRepoImp(RestModule restModule) {
        this.restModule = restModule;
    }

    @Override
    public Single<Map<Integer, ItemPriceInfo>> getPriceInfoList(List<String> typeId, final int regionId) {
        return Observable.fromIterable(typeId)
                .flatMap(new Function<String, Observable<List<MarketOrder>>>() {
                    @Override
                    public Observable<List<MarketOrder>> apply(String s) throws Exception {
                        return restModule.getMarketOrder(s, regionId);
                    }
                })
                .flatMap(new Function<List<MarketOrder>, Observable<ItemPriceInfo>>() {
                    @Override
                    public Observable<ItemPriceInfo> apply(List<MarketOrder> marketOrders) throws Exception {

                        List<MarketOrder> sellList = new ArrayList<>();
                        List<MarketOrder> buyList = new ArrayList<>();

                        for(MarketOrder order: marketOrders){
                            if (order.isBuyOrder()) {
                                buyList.add(order);
                            } else {
                                sellList.add(order);
                            }
                        }

                        MarketOrder buy = Collections.max(buyList);
                        MarketOrder sell = Collections.min(sellList);

                        ItemPriceInfo priceInfo = new ItemPriceInfo(
                                sell.getPrice(),
                                buy.getPrice(),
                                sell.getTypeId()
                        );
                        return Observable.just(priceInfo);
                    }
                })
                .toMap(new Function<ItemPriceInfo, Integer>() {
                    @Override
                    public Integer apply(ItemPriceInfo itemPriceInfo) throws Exception {
                        return itemPriceInfo.getTypeId();
                    }
                });
    }


}
