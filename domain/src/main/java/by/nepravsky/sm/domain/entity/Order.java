package by.nepravsky.sm.domain.entity;

public class Order {

    private double price;
    private String typeId;
    private boolean isBuyOrder;
    private int systemId;

    public Order(double price, String typeId, boolean isBuyOrder, int systemId) {
        this.price = price;
        this.typeId = typeId;
        this.isBuyOrder = isBuyOrder;
        this.systemId = systemId;
    }

    public int getSystemId() {
        return systemId;
    }

    public double getPrice() {
        return price;
    }

    public String getTypeId() {
        return typeId;
    }

    public boolean isBuyOrder() {
        return isBuyOrder;
    }
}
