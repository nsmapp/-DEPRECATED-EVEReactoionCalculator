package by.nepravsky.sm.domain.usecase;

import java.util.List;

import javax.inject.Inject;

import by.nepravsky.sm.domain.entity.presentation.FormulaName;
import by.nepravsky.sm.domain.entity.presentation.FormulaTypes;
import by.nepravsky.sm.domain.executors.PostExecutionThread;
import by.nepravsky.sm.domain.repositories.FormulaRepositories;
import io.reactivex.Single;
import io.reactivex.functions.Function3;

public class GetFormulaNamesUseCase  extends BaseUseCase{

    private FormulaRepositories formulaRepositories;

    @Inject
    public GetFormulaNamesUseCase(PostExecutionThread postExecutionThread, FormulaRepositories formulaRepositories) {
        super(postExecutionThread);
        this.formulaRepositories = formulaRepositories;
    }

    public Single<FormulaTypes> getFormulaTypes(){

        Single<List<FormulaName>> boosters = formulaRepositories.getBoosters();
        Single<List<FormulaName>> fullerites = formulaRepositories.getFullerites();
        Single<List<FormulaName>> composites = formulaRepositories.getAllComposites();
        Single<FormulaTypes> zip = Single.zip(
                boosters,
                fullerites,
                composites,
                new Function3<List<FormulaName>, List<FormulaName>, List<FormulaName>, FormulaTypes>() {
                    @Override
                    public FormulaTypes apply(List<FormulaName> boosters,
                                              List<FormulaName> fullerites,
                                              List<FormulaName> composite) throws Exception {
                        return new FormulaTypes(boosters, fullerites, composite);
                    }
                })
                .subscribeOn(executionThread)
                .observeOn(postExecutionThread);

        return zip;

    }
    
}
