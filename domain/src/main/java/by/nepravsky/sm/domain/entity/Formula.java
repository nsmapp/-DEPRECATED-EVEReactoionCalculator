package by.nepravsky.sm.domain.entity;

import java.util.List;

public class Formula {

    private int id;
    private String name = "";
    private List<ReactionItem> material;
    private ReactionItem product;
    private int time = 0;

    public Formula(int id, String name, List<ReactionItem> material, ReactionItem product, int time) {
        this.id = id;
        this.name = name;
        this.material = material;
        this.product = product;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ReactionItem> getMaterial() {
        return material;
    }

    public ReactionItem getProduct() {
        return product;
    }

    public int getTime() {
        return time;
    }
}
