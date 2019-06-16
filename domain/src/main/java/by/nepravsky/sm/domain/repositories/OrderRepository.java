package by.nepravsky.sm.domain.repositories;

import java.util.List;

import by.nepravsky.sm.domain.entity.Order;
import io.reactivex.Observable;

public interface OrderRepository {


    Observable<List<List<Order>>> getOrderList(List<String> typeId, int regionId);
}
