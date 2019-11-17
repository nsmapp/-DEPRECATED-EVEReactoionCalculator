package by.nepravsky.sm.data.net;

import java.util.List;

import by.nepravsky.sm.data.net.entity.MarketOrder;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestApi {

    @GET("legacy/markets/{region_id}/orders/")
    Observable<List<MarketOrder>> getOrders(@Path("region_id") int regionId,
                                            @Query("type_id") String typeId);

}
