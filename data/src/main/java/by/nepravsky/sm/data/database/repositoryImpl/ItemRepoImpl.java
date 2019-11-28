package by.nepravsky.sm.data.database.repositoryImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import by.nepravsky.sm.data.database.AppDatabase;
import by.nepravsky.sm.data.database.entity.ItemDBE;
import by.nepravsky.sm.domain.entity.ItemInfo;
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
    public Single<Map<Integer, ItemInfo>> getItemList(List<Integer> idList) {
        return appDatabase.getItemDAO()
                .getItemList(idList)
                .map(new Function<List<ItemDBE>, Map<Integer, ItemInfo>>() {
                    @Override
                    public Map<Integer, ItemInfo> apply(List<ItemDBE> items) throws Exception {

                        Map<Integer, ItemInfo> itemMap = new HashMap<>();
                        for(ItemDBE item : items){
                            ItemInfo domainItemInfo = new ItemInfo(
                                    Integer.valueOf(item.getId()),
                                    item.getEn(),
                                    item.getVolume(),
                                    item.getBasePrice()
                            );
                            itemMap.put(domainItemInfo.getId(), domainItemInfo);
                        }

                        return itemMap;
                    }
                });
    }
}
