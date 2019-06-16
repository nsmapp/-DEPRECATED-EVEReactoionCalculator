package by.nepravsky.sm.domain.entity;

public class Material {

    private String name = "no name";
    private String id;
    private double vol;
    private double basePrice;


    public Material(String name, String id, double vol, double basePrice) {
        this.name = name;
        this.id = id;
        this.vol = vol;
        this.basePrice = basePrice;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public double getVol() {
        return vol;
    }
}
