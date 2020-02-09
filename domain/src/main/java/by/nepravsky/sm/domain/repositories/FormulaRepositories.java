package by.nepravsky.sm.domain.repositories;

import java.util.List;

import by.nepravsky.sm.domain.entity.Formula;
import by.nepravsky.sm.domain.entity.presentation.FormulaName;
import io.reactivex.Single;

public interface FormulaRepositories {

    Single<Formula> getFormula(String name);
    Single<List<Formula>> getAllFormuls();
    Single<List<FormulaName>> getBoosters();
    Single<List<FormulaName>> getFullerites();
    Single<List<FormulaName>> getAllComposites();
}
