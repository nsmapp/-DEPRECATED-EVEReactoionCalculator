package by.nepravsky.sm.domain.usecase;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import by.nepravsky.sm.domain.entity.ItemInfo;
import by.nepravsky.sm.domain.executors.PostExecutionThread;
import by.nepravsky.sm.domain.repositories.ItemRepositories;
import io.reactivex.Observable;

public class GetItemMapUseCase extends BaseUseCase{

    private ItemRepositories itemRepositories;

    @Inject
    public GetItemMapUseCase(PostExecutionThread postExecutionThread, ItemRepositories itemRepositories) {
        super(postExecutionThread);
        this.itemRepositories = itemRepositories;
    }

    public Observable<Map<Integer, ItemInfo>> getAsync(List<Integer> idList){
        return itemRepositories.getItemList(idList)
                .subscribeOn(executionThread)
                .observeOn(postExecutionThread)
                .toObservable();

    }
}
