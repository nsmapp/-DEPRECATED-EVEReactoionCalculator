package by.nepravsky.sm.domain.usecase;

import java.util.List;

import javax.inject.Inject;

import by.nepravsky.sm.domain.entity.Formula;
import by.nepravsky.sm.domain.executors.PostExecutionThread;
import by.nepravsky.sm.domain.repositories.FormulaRepositories;
import io.reactivex.Observable;


public class GetFormulaUseCase extends BaseUseCase{

    private FormulaRepositories formulaRepositories;

    @Inject
    public GetFormulaUseCase(PostExecutionThread postExecutionThread, FormulaRepositories formulaRepositories) {
        super(postExecutionThread);
        this.formulaRepositories = formulaRepositories;
    }

    public Observable<Formula> getAsync(String formulaName){
        return formulaRepositories
                .getFormula(formulaName)
                .subscribeOn(executionThread)
                .observeOn(postExecutionThread)
                .toObservable();
    }

    public Observable<List<Formula>> getAll(){
        return formulaRepositories
                .getAllFormuls()
                .subscribeOn(executionThread)
                .observeOn(postExecutionThread)
                .toObservable();
    }
}
