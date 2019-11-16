package by.nepravsky.sm.data.database.repositoryImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import by.nepravsky.sm.data.database.AppDatabase;
import by.nepravsky.sm.data.database.entity.ItemDBE;
import by.nepravsky.sm.domain.entity.Material;
import by.nepravsky.sm.domain.repositories.ItemRepositories;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class ItemRepoImpl implements ItemRepositories {


    private AppDatabase appDatabase;

    @Inject
    public ItemRepoImpl(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;

    }


    @Override
    public Observable<Map<String, Material>> getItemList(List<String> idList) {
        return appDatabase
                .getMaterialDAO()
                .getItemList(idList)
                .toObservable()
                .map(new Function<List<ItemDBE>, Map<String, Material>>() {
                    @Override
                    public Map<String, Material> apply(List<ItemDBE> itemDBES) throws Exception {

                        Map<String, Material> materialMap = new HashMap<>();
                        for (ItemDBE m : itemDBES){
                            materialMap.put(m.getId(),
                                    new Material(m.getEn(),
                                            m.getId(),
                                            m.getVolume(),
                                            m.getBasePrice()));
                        }

                        return materialMap;
                    }
                });
    }
}
