package by.nepravsky.sm.domain.entity;

public class ReactionItem {

    public ReactionItem(int quantity, int typeID) {
        this.quantity = quantity;
        this.typeID = typeID;
    }

    private int quantity;
    private int typeID;

    public int getQuantity() {
        return quantity;
    }

    public int getTypeID() {
        return typeID;
    }
}
