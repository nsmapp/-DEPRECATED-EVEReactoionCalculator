package by.nepravsky.sm.domain.usecase.downlevel.caseinterface;

import java.util.List;
import java.util.Map;

import by.nepravsky.sm.domain.entity.Material;
import io.reactivex.Observable;

public interface MaterialMapCase {

    Observable<Map<String, Material>> get(List<String> idList);
}
