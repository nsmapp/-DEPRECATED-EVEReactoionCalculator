package by.nepravsky.sm.data.net.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MarketOrder implements Comparable<MarketOrder> {

    @SerializedName("duration")
    @Expose
    private long duration;
    @SerializedName("is_buy_order")
    @Expose
    private boolean isBuyOrder;
    @SerializedName("issued")
    @Expose
    private String issued;
    @SerializedName("location_id")
    @Expose
    private long locationId;
    @SerializedName("min_volume")
    @Expose
    private double minVolume;
    @SerializedName("order_id")
    @Expose
    private long orderId;
    @SerializedName("price")
    @Expose
    private double price;
    @SerializedName("range")
    @Expose
    private String range;
    @SerializedName("system_id")
    @Expose
    private int systemId;
    @SerializedName("type_id")
    @Expose
    private int typeId;
    @SerializedName("volume_remain")
    @Expose
    private long volumeRemain;
    @SerializedName("volume_total")
    @Expose
    private long volumeTotal;



    public boolean isBuyOrder() {
        return isBuyOrder;
    }

    public long getLocationId() {
        return locationId;
    }

    public int getTypeId() {
        return typeId;
    }

    public double getPrice() {
        return price;
    }

    public int getSystemId() {
        return systemId;
    }

    @Override
    public int compareTo(MarketOrder o) {
        return (int) (this.price - o.price);
    }
}
