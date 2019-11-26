package by.nepravsky.sm.domain.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import by.nepravsky.sm.domain.entity.Formula;
import by.nepravsky.sm.domain.entity.ItemInfo;
import by.nepravsky.sm.domain.entity.ItemPriceInfo;
import by.nepravsky.sm.domain.entity.ReactionItem;
import by.nepravsky.sm.domain.entity.presentation.ItemPres;
import by.nepravsky.sm.domain.entity.presentation.ReactionPres;

public class ReactionUtils {

    @Inject
    public ReactionUtils() {
    }


    public ReactionPres makeReactionPres(
            int reactionTime,
            ItemPres product,
            List<ItemPres> materialList){

        double materialSellPrice = 0;
        double materialBuyPrice = 0;
        double materialVolume = 0;

        for (ItemPres item : materialList){
            materialSellPrice = materialSellPrice + item.getSellPrice();
            materialBuyPrice = materialBuyPrice + item.getBuyPrice();
            materialVolume = materialVolume + item.getVolume();

        }

        return  new ReactionPres(
                product,
                materialList,materialSellPrice,
                materialBuyPrice,
                reactionTime,
                materialVolume);
    }

    public List<ItemPres> makeItemPres(List<ReactionItem> reactionItems,
                                        Map<Integer, ItemInfo> itemInfoMap,
                                        Map<Integer, ItemPriceInfo> priceMap){

        List<ItemPres> itemPresList = new ArrayList<>();

        int id;
        for(ReactionItem i : reactionItems){
            id = i.getTypeID();
            ItemPres itemPres = new ItemPres(
                    id,
                    itemInfoMap.get(id).getName(),
                    itemInfoMap.get(id).getVolume(),
                    i.getQuantity(),
                    priceMap.get(id).getSell(),
                    priceMap.get(id).getBuy()
            );
            itemPresList.add(itemPres);
        }

        return itemPresList;
    }


    public List<String> getIdList(List<ReactionItem> reactionItemList) {
        List<String> idList  = new ArrayList<>();
        for (ReactionItem item : reactionItemList){
            idList.add(String.valueOf(item.getTypeID()));
        }
        return idList;
    }

    public Formula getReactionWithName(String formulaName, Map<Integer, Formula> formulaMap){

        for(int formulaId: formulaMap.keySet()){
            if (formulaMap.get(formulaId).getName().equals(formulaName)){
                return formulaMap.get(formulaId);
            }
        }

        Formula emptyFormula = new Formula(0,
                "no_name",
                new ArrayList<ReactionItem>(),
                new ReactionItem(0, 0),
                0);
        return emptyFormula;
    }

    public Boolean isSubProduct(ReactionItem item, Map<Integer, Formula> formulaMap){
        return formulaMap.containsKey(item.getTypeID());
    }

}
