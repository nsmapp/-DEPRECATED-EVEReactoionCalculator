package by.nepravsky.sm.domain.usecase.downlevel;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import by.nepravsky.sm.domain.entity.Material;
import by.nepravsky.sm.domain.executors.PostExecutionThread;
import by.nepravsky.sm.domain.repositories.ItemRepositories;
import by.nepravsky.sm.domain.usecase.BaseUseCase;
import by.nepravsky.sm.domain.usecase.downlevel.caseinterface.MaterialMapCase;
import io.reactivex.Observable;

public class GetMaterialMap  extends BaseUseCase implements MaterialMapCase {

    private ItemRepositories itemRepositories;

    @Inject
    public GetMaterialMap(PostExecutionThread postExecutionThread, ItemRepositories itemRepositories) {
        super(postExecutionThread);
        this.itemRepositories = itemRepositories;
    }

    public Observable<Map<String, Material>> get(List<String> idList){
        return itemRepositories
                .getItemList(idList)
                .subscribeOn(executionThread)
                .observeOn(postExecutionThread);
    }


}
