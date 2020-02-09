package by.nepravsky.sm.domain.entity.presentation;

import java.util.ArrayList;
import java.util.List;

public class FormulaTypes {

    private List<FormulaName> boosters = new ArrayList<>();
    private List<FormulaName> fullerides = new ArrayList<>();
    private List<FormulaName> composite = new ArrayList<>();
    private List<FormulaName> all = new ArrayList<>();

    public FormulaTypes(List<FormulaName> boosters, List<FormulaName> fullerides, List<FormulaName> composite) {
        this.boosters = boosters;
        this.fullerides = fullerides;
        this.composite = composite;
        this.all.addAll(boosters);
        this.all.addAll(fullerides);
        this.all.addAll(composite);
    }

    public List<FormulaName> getBoosters() {
        return boosters;
    }

    public List<FormulaName> getFullerides() {
        return fullerides;
    }

    public List<FormulaName> getComposite() {
        return composite;
    }

    public List<FormulaName> getAll() {
        return all;
    }
}
