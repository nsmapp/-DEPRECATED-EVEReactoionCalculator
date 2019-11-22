package by.nepravsky.sm.domain.entity.presentation;

import java.util.Locale;

import by.nepravsky.sm.domain.entity.DomainEntity;
import by.nepravsky.sm.domain.utils.StringUtils;

public class ItemPres implements DomainEntity {

    private int id;
    private String name = "";
    private double vol;
    private double quantity;
    private double sellPrice;
    private double buyPrice;

    public ItemPres(int id, String name, double vol, double quantity, double sellPrice, double buyPrice) {
        this.id = id;
        this.name = name;
        this.vol = vol * quantity;
        this.quantity = quantity;
        this.sellPrice = sellPrice * quantity;
        this.buyPrice = buyPrice * quantity;
    }

    public void setRuns(int runs){
        vol = vol * runs;
        sellPrice = sellPrice * runs;
        buyPrice = buyPrice * runs;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getVolume() {
        return vol;
    }

    public String getVolumeString() {
        return StringUtils.formatDouble(vol);
    }

    public double getQuantity() {
        return quantity;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public String getSellPriceString() {
        return StringUtils.formatDouble(sellPrice);
    }

    public String getBuyPriceString() {
        return StringUtils.formatDouble(buyPrice);
    }
}
