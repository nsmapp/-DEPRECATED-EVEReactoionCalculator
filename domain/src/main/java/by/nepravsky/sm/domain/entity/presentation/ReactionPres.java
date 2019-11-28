package by.nepravsky.sm.domain.entity.presentation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import by.nepravsky.sm.domain.utils.StringUtils;

public class ReactionPres {

    private ItemPres product;
    private List<ItemPres> materialList = new ArrayList<>();
    private double materialSellPrice;
    private double materialBuyPrice;
    private double reactiomCost;
    private int reactionTime;
    private double matVolume;

    public ReactionPres(ItemPres product,
                        List<ItemPres> materialList,
                        double materialSellPrice,
                        double materialBuyPrice,
                        int reactionTime,
                        double matVolume,
                        double reactiomCost) {
        this.product = product;
        this.materialList = materialList;
        this.materialSellPrice = materialSellPrice;
        this.materialBuyPrice = materialBuyPrice;
        this.reactionTime = reactionTime;
        this.matVolume = matVolume;
        this.reactiomCost = reactiomCost;
    }


    public ItemPres getProduct() {
        return product;
    }

    public List<ItemPres> getMaterialList() {
        return materialList;
    }

    public String getMaterialSellPrice(){
        return StringUtils.formatDouble(materialSellPrice);
    }

    public String getMaterialBuyPrice() {
        return StringUtils.formatDouble(materialBuyPrice);
    }

    public String getReactiomCost() {
        return StringUtils.formatDouble(reactiomCost);
    }

    public int getReactionTime() {
        return reactionTime;
    }

    public String getMatVolume() {
        return StringUtils.formatDouble(matVolume);
    }
}
