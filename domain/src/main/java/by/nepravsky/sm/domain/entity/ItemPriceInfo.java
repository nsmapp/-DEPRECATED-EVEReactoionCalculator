package by.nepravsky.sm.domain.entity;

public class ItemPriceInfo {

    private double sell;
    private double buy;

    private int typeId;

    public ItemPriceInfo(double sell, double buy,  int typeId) {
        this.sell = sell;
        this.buy = buy;
        this.typeId = typeId;
    }

    public double getSell() {
        return sell;
    }

    public double getBuy() {
        return buy;
    }

    public int getTypeId() {
        return typeId;
    }
}
