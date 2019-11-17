package by.nepravsky.sm.data.database.repositoryImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import by.nepravsky.sm.data.database.AppDatabase;
import by.nepravsky.sm.data.database.entity.ItemDBE;
import by.nepravsky.sm.domain.entity.Item;
import by.nepravsky.sm.domain.repositories.ItemRepositories;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public class ItemRepoImpl implements ItemRepositories {

    private AppDatabase appDatabase;

    @Inject
    public ItemRepoImpl(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;

    }

    @Override
    public Single<Map<Integer, Item>> getItemList(List<String> idList) {
        return appDatabase.getItemDAO()
                .getItemList(idList)
                .map(new Function<List<ItemDBE>, Map<Integer, Item>>() {
                    @Override
                    public Map<Integer, Item> apply(List<ItemDBE> items) throws Exception {

                        Map<Integer, Item> itemMap = new HashMap<>();
                        for(ItemDBE item : items){
                            Item domainItem = new Item(
                                    Integer.valueOf(item.getId()),
                                    item.getEn(),
                                    item.getVolume(),
                                    item.getBasePrice()
                            );
                            itemMap.put(domainItem.getId(), domainItem);
                        }

                        return itemMap;
                    }
                });
    }
}
