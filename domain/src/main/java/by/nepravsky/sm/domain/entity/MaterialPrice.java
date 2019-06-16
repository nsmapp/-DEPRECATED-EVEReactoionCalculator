package by.nepravsky.sm.domain.entity;

public class MaterialPrice {

    private double buy;
    private double sell;

    public MaterialPrice(double buy, double sell) {
        this.buy = buy;
        this.sell = sell;
    }

    public double getBuy() {
        return buy;
    }

    public double getSell() {
        return sell;
    }
}
