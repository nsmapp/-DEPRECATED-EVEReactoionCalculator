package by.nepravsky.sm.domain.repositories;

import java.util.List;
import java.util.Map;

import by.nepravsky.sm.domain.entity.ItemInfo;
import io.reactivex.Single;

public interface ItemRepositories {

    Single<Map<Integer, ItemInfo>> getItemList(List<String> idList);
}
