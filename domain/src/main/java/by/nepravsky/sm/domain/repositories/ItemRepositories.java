package by.nepravsky.sm.domain.repositories;

import java.util.List;
import java.util.Map;

import by.nepravsky.sm.domain.entity.Material;
import io.reactivex.Observable;

public interface ItemRepositories {

    Observable<Map<String, Material>> getItemList(List<String> idList);
}
