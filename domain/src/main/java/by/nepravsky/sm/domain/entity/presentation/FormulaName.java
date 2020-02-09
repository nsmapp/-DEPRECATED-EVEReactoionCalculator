package by.nepravsky.sm.domain.entity.presentation;

public class FormulaName {

    private int id;
    private String name = "";

    public FormulaName(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
