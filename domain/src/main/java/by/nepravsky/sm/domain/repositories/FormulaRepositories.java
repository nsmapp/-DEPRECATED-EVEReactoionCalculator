package by.nepravsky.sm.domain.repositories;

import java.util.List;

import by.nepravsky.sm.domain.entity.Formula;
import io.reactivex.Single;

public interface FormulaRepositories {

    Single<Formula> getFormula(String name);
    Single<List<Formula>> getAllFormuls();
}
