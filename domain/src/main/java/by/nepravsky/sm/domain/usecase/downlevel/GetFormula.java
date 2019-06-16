package by.nepravsky.sm.domain.usecase.downlevel;

import javax.inject.Inject;

import by.nepravsky.sm.domain.entity.Formula;
import by.nepravsky.sm.domain.executors.PostExecutionThread;
import by.nepravsky.sm.domain.repositories.FormulaRepositories;

import by.nepravsky.sm.domain.usecase.BaseUseCase;
import by.nepravsky.sm.domain.usecase.downlevel.caseinterface.FormulaCase;
import io.reactivex.Observable;

public class GetFormula extends BaseUseCase implements FormulaCase {

    private  FormulaRepositories formulaRepositories;

    @Inject
    public GetFormula(PostExecutionThread postExecutionThread, FormulaRepositories formulaRepositories) {
        super(postExecutionThread);
        this.formulaRepositories = formulaRepositories;
    }

    public Observable<Formula> get(String name){
        return formulaRepositories.getFormula(name)
                .subscribeOn(executionThread)
                .observeOn(postExecutionThread);
    }
}
