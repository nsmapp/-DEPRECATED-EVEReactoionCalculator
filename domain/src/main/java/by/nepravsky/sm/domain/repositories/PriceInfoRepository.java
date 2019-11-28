package by.nepravsky.sm.domain.repositories;

import java.util.List;
import by.nepravsky.sm.domain.entity.ItemPriceInfo;
import io.reactivex.Single;

public interface PriceInfoRepository {


    Single<List<ItemPriceInfo>> getPriceInfoList(List<Integer> typeId, int regionId);
}
