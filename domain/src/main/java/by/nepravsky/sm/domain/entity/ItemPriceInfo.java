package by.nepravsky.sm.domain.entity;

public class ItemPriceInfo {

    private double sell = 0;
    private double buy = 0;
    private int typeId ;

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
