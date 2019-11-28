package by.nepravsky.sm.domain.entity;

public class ReactionItem {


    private int reactionQuantity;
    private int quantity;
    private int typeID;
    public ReactionItem(int quantity, int typeID) {
        this.quantity = quantity;
        this.reactionQuantity = quantity;
        this.typeID = typeID;

    }

    public void initRuns(int run){
        quantity = reactionQuantity * run;
    }

    public int getReactionQuantity() {
        return reactionQuantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getId() {
        return typeID;
    }
}
