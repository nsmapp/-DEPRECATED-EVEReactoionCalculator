package by.nepravsky.sm.data.net.repoimpl;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import by.nepravsky.sm.data.net.RestModule;
import by.nepravsky.sm.data.net.entity.MaterialOrder;
import by.nepravsky.sm.domain.entity.Order;
import by.nepravsky.sm.domain.repositories.OrderRepository;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class OrderRepoImp implements OrderRepository {


    private RestModule restModule;

    @Inject
    public OrderRepoImp(RestModule restModule) {
        this.restModule = restModule;
    }


    @Override
    public Observable<List<List<Order>>> getOrderList(List<String> typeIds, int regionId) {

        List<Observable<List<Order>>> observables = new ArrayList<>();

        for (String id: typeIds) {

            Observable<List<Order>> observable =
                    restModule.getMarketOrder(id, regionId)
                    .map(new Function<List<MaterialOrder>, List<Order>>() {
                        @Override
                        public List<Order> apply(List<MaterialOrder> materialOrders) throws Exception {
                            return mapOrder(materialOrders);
                        }
                    });

            observables.add(observable);

        }

        return Observable.zip(observables, new Function<Object[], List<List<Order>>>() {
            @Override
            public List<List<Order>> apply(Object[] objects) throws Exception {

                List<List<Order>> orderList = new ArrayList<>();
                for (Object o: objects){
                    List<Order> orders = (List<Order>) o;
                    orderList.add(orders);
                }

                return orderList;
            }
        });
    }


    private List<Order> mapOrder(List<MaterialOrder> materialOrders){

        List<Order> orderList = new ArrayList<>();

        for(MaterialOrder order : materialOrders){
            Order o = new Order(
                    order.getPrice(),
                    String.valueOf(order.getTypeId()),
                    order.isBuyOrder(),
                    order.getSystemId()
            );
            orderList.add(o);
        }

        return orderList;
    }


}
