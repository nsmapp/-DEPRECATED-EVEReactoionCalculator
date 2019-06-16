package by.nepravsky.sm.domain.usecase.downlevel;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import by.nepravsky.sm.domain.entity.Material;
import by.nepravsky.sm.domain.executors.PostExecutionThread;
import by.nepravsky.sm.domain.repositories.MaterialRepositories;
import by.nepravsky.sm.domain.usecase.BaseUseCase;
import by.nepravsky.sm.domain.usecase.downlevel.caseinterface.MaterialMapCase;
import io.reactivex.Observable;

public class GetMaterialMap  extends BaseUseCase implements MaterialMapCase {

    private MaterialRepositories materialRepositories;

    @Inject
    public GetMaterialMap(PostExecutionThread postExecutionThread, MaterialRepositories materialRepositories) {
        super(postExecutionThread);
        this.materialRepositories = materialRepositories;
    }

    public Observable<Map<String, Material>> get(List<String> idList){
        return materialRepositories
                .getMaterialList(idList)
                .subscribeOn(executionThread)
                .observeOn(postExecutionThread);
    }


}
