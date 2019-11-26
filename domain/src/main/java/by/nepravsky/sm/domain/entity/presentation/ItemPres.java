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
        quantity = quantity * runs;
        vol = vol * quantity;
        sellPrice = sellPrice * quantity;
        buyPrice = buyPrice * quantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public double getBuyPrice() {
        return buyPrice;
    }
    public double getVolume() {
        return vol;
    }

    public double getSellPriceForUnit() {
        return sellPrice / quantity;
    }

    public double getBuyPriceForUnit() {
        return buyPrice / quantity;
    }
    public double getVolumeForUnit() {
        return vol / quantity;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getVolumeString() {
        return StringUtils.formatDouble(vol);
    }

    public String getSellPriceString() {
        return StringUtils.formatDouble(sellPrice);
    }

    public String getBuyPriceString() {
        return StringUtils.formatDouble(buyPrice);
    }
}
