package by.nepravsky.sm.domain.usecase.downlevel.caseinterface;

import java.util.List;
import java.util.Map;

import by.nepravsky.sm.domain.entity.MaterialPrice;
import io.reactivex.Observable;

public interface MaterialPriceMapCase {

    Observable<Map<String, MaterialPrice>> get(List<String> typeID, final int systemId);

    }
