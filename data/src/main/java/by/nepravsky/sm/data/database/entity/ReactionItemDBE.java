package by.nepravsky.sm.data.database.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReactionItemDBE {

    @SerializedName("quantity")
    @Expose
    private int quantity;
    @SerializedName("typeID")
    @Expose
    private int typeID;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

}
