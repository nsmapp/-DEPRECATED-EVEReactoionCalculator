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
    private int reactionTime;
    private double matVolume;

    public ReactionPres(ItemPres product,
                        List<ItemPres> materialList,
                        double materialSellPrice,
                        double materialBuyPrice,
                        int reactionTime,
                        double matVolume) {
        this.product = product;
        this.materialList = materialList;
        this.materialSellPrice = materialSellPrice;
        this.materialBuyPrice = materialBuyPrice;
        this.reactionTime = reactionTime;
        this.matVolume = matVolume;
    }


    public ItemPres getProduct() {
        return product;
    }

    public List<ItemPres> getMaterialList() {
        return materialList;
    }

    public String getMaterialSellPrice() {
        if(materialSellPrice == 0){
            return "-";
        }else {
            return StringUtils.formatDouble(materialSellPrice);
        }
    }

    public String getMaterialBuyPrice() {
        if(materialBuyPrice == 0){
            return "-";
        }else {
            return StringUtils.formatDouble(materialBuyPrice);
        }
    }

    public int getReactionTime() {
        return reactionTime;
    }

    public String getMatVolume() {
        return StringUtils.formatDouble(matVolume);
    }
}
