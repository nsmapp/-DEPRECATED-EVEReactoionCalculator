package by.nepravsky.sm.domain.repositories;

import by.nepravsky.sm.domain.entity.Formula;
import io.reactivex.Observable;

public interface FormulaRepositories {

    Observable<Formula> getFormula(String name);


}
