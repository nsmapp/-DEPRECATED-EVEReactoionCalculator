package by.nepravsky.sm.data.net.repoimpl;

import android.util.Log;

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
    public Single<List<ItemPriceInfo>> getPriceInfoList(List<Integer> typeId, final int regionId) {
        return Single.just(typeId)
                .flatMap(new Function<List<Integer>, Single<List<ItemPriceInfo>>>() {
                    @Override
                    public Single<List<ItemPriceInfo>> apply(List<Integer> ids) throws Exception {

                        List<Single<List<MarketOrder>>>  singles = new ArrayList<>();
                        for(int id : ids){
                            singles.add(restModule.getMarketOrder(id, regionId));
                        }
                        return Single.zip(singles, new Function<Object[], List<ItemPriceInfo>>() {
                            @Override
                            public List<ItemPriceInfo> apply(Object[] objects) throws Exception {

                                int i = 0;
                                List<ItemPriceInfo> priceInfo = new ArrayList<>();
                                for(Object o : objects){
                                    if (((List<MarketOrder>) o).isEmpty()){
                                        continue;
                                    }
                                    priceInfo.add(mapToDomainEntity((List<MarketOrder>) o));
                                }
                                return priceInfo;
                            }
                        });
                    }
                });
    }

    private ItemPriceInfo mapToDomainEntity(List<MarketOrder> orderList){

        List<MarketOrder> sellList = new ArrayList<>();
        List<MarketOrder> buyList = new ArrayList<>();

        if(!orderList.isEmpty()){
            for(MarketOrder order : orderList){
                if(order.isBuyOrder()){
                    buyList.add(order);
                }else {
                    sellList.add(order);
                }
            }
        }

        double sellPrice, buyPrice;

        if (sellList.isEmpty()){
            sellPrice = 0;
        }else{
            sellPrice = Collections.min(sellList).getPrice();
        }

        if(buyList.isEmpty()){
            buyPrice = 0;
        }else{
            buyPrice = Collections.max(buyList).getPrice();
        }

        return new ItemPriceInfo(
                sellPrice,
                buyPrice,
                orderList.get(0).getTypeId()
        );
    }


}
