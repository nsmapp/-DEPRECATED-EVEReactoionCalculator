package by.nepravsky.sm.domain.entity;

public class Item {

    private int id;
    private String name = "";
    private double volume = 0.0;
    private double basePrice = 0.0;

    public Item(int id, String name, double volume, double basePrice) {
        this.id = id;
        this.name = name;
        this.volume = volume;
        this.basePrice = basePrice;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getVolume() {
        return volume;
    }

    public double getBasePrice() {
        return basePrice;
    }
}
