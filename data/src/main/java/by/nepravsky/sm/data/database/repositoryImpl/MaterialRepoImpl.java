package by.nepravsky.sm.data.database.repositoryImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import by.nepravsky.sm.data.database.AppDatabase;
import by.nepravsky.sm.data.database.entity.MaterialDBE;
import by.nepravsky.sm.domain.entity.Material;
import by.nepravsky.sm.domain.repositories.MaterialRepositories;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class MaterialRepoImpl implements MaterialRepositories {


    private AppDatabase appDatabase;

    @Inject
    public MaterialRepoImpl(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;

    }


    @Override
    public Observable<Map<String, Material>> getMaterialList(List<String> idList) {
        return appDatabase
                .getMaterialDAO()
                .getMaterialList(idList)
                .toObservable()
                .map(new Function<List<MaterialDBE>, Map<String, Material>>() {
                    @Override
                    public Map<String, Material> apply(List<MaterialDBE> materialDBES) throws Exception {

                        Map<String, Material> materialMap = new HashMap<>();
                        for (MaterialDBE m : materialDBES){
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
