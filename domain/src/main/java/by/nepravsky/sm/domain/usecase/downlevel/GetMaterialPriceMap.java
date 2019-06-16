package by.nepravsky.sm.domain.usecase.downlevel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import by.nepravsky.sm.domain.entity.MaterialPrice;
import by.nepravsky.sm.domain.entity.Order;
import by.nepravsky.sm.domain.executors.PostExecutionThread;
import by.nepravsky.sm.domain.repositories.OrderRepository;
import by.nepravsky.sm.domain.usecase.BaseUseCase;
import by.nepravsky.sm.domain.usecase.downlevel.caseinterface.MaterialPriceMapCase;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class GetMaterialPriceMap extends BaseUseCase implements MaterialPriceMapCase {


    private OrderRepository orderRepository;

    @Inject
    public GetMaterialPriceMap(PostExecutionThread postExecutionThread,
                               OrderRepository orderRepository) {
        super(postExecutionThread);
        this.orderRepository = orderRepository;
    }



    public Observable<Map<String, MaterialPrice>> get(List<String> typeID, final int regionId){
        return orderRepository
                .getOrderList(typeID, regionId)
                .subscribeOn(executionThread)
                .observeOn(postExecutionThread)
                .map(new Function<List<List<Order>>, Map<String, MaterialPrice>>() {
                    @Override
                    public Map<String, MaterialPrice> apply(List<List<Order>> orders) throws Exception {

//                        for (List<Order> lo: orders) {
//                            filterBySystem(lo, regionId);
//                        }

                        Map<String, MaterialPrice> priceMap = new HashMap<>();

                        for (List<Order> lo: orders) {

                            if (lo != null && lo.size() != 0){
                                MaterialPrice price = getPrice(lo);
                                priceMap.put(lo.get(0).getTypeId(), price);
                            }

                        }
                        return priceMap;
                    }
                });
    }

    private List<Order> filterBySystem(List<Order> orders, int systemId){

        List<Order> filtrOrder = new ArrayList<>();

        for (Order o: orders) {
            if (o.getSystemId() == systemId){
                filtrOrder.add(o);
            }
        }

        return filtrOrder;
    }

    private MaterialPrice getPrice(List<Order> orders){

        double sell = orders.get(0).getPrice(), buy = 0;

        for (Order lo: orders) {

            if (lo.isBuyOrder()){
                if (lo.getPrice() > buy){
                    buy = lo.getPrice();
                }
            }else {
                if (lo.getPrice() < sell){
                    sell = lo.getPrice();
                }
            }

        }

        return new MaterialPrice(buy, sell);
    }
}
