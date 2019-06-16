package by.nepravsky.sm.domain.usecase.downlevel.caseinterface;

import by.nepravsky.sm.domain.entity.Formula;
import io.reactivex.Observable;

public interface FormulaCase {

    Observable<Formula> get(String name);
}
