package by.nepravsky.sm.domain.repositories;

import java.util.List;
import java.util.Map;

import by.nepravsky.sm.domain.entity.ItemPriceInfo;
import io.reactivex.Single;

public interface PriceInfoRepository {


    Single<Map<Integer, ItemPriceInfo>> getPriceInfoList(List<String> typeId, int regionId);
}
