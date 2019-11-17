package by.nepravsky.sm.domain.repositories;

import by.nepravsky.sm.domain.entity.Formula;
import io.reactivex.Single;

public interface FormulaRepositories {

    Single<Formula> getFormula(String name);
}
