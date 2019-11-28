package by.nepravsky.sm.domain.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
            List<ItemPres> materialList,
            List<ItemPres> subProductList,
            double reactionTax){

        double materialSellPrice = 0;
        double materialBuyPrice = 0;
        double reactionRunsPrice = 0;
        double materialVolume = 0;
        double tax = reactionTax / 100;
        boolean isSellZero = false, isBuyZero = false;

        for (ItemPres item : materialList){
            if (item.getBuyPrice() == 0){isBuyZero = true;}
            if (item.getSellPrice() == 0){isSellZero = true;}
            materialSellPrice = materialSellPrice + item.getSellPrice();
            materialBuyPrice = materialBuyPrice + item.getBuyPrice();
            materialVolume = materialVolume + item.getVolume();

        }

        if (isBuyZero){materialBuyPrice = 0;}
        if (isSellZero){materialSellPrice = 0;}

        for (ItemPres item: subProductList){
            reactionRunsPrice = reactionRunsPrice + item.getBuyPrice() * tax;
        }

        return  new ReactionPres(
                product,
                materialList,materialSellPrice,
                materialBuyPrice,
                reactionTime,
                materialVolume,
                reactionRunsPrice);
    }

    public List<ItemPres> makeItemPres(List<ReactionItem> reactionItems,
                                        Map<Integer, ItemInfo> itemInfoMap,
                                        Map<Integer, ItemPriceInfo> priceMap){

        List<ItemPres> itemPresList = new ArrayList<>();

        int id;
        for(ReactionItem i : reactionItems){
            id = i.getId();
            int run = i.getReactionQuantity() / i.getQuantity();
            ItemPres itemPres = new ItemPres(
                    id,
                    itemInfoMap.get(id).getName(),
                    itemInfoMap.get(id).getVolume(),
                    i.getQuantity(),
                    priceMap.get(id).getSell(),
                    priceMap.get(id).getBuy(),
                    itemInfoMap.get(id).getBasePrice() * run
            );
            itemPresList.add(itemPres);
        }

        return itemPresList;
    }


    public List<Integer> getIdList(List<ReactionItem> reactionItemList) {
        List<Integer> idList  = new ArrayList<>();
        for (ReactionItem item : reactionItemList){
            idList.add(item.getId());
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
        return formulaMap.containsKey(item.getId());
    }

    public List<ItemPres> colapseItemPres(List<ItemPres> materialPres){
        List<ItemPres> collapseList = new ArrayList<>();
        Set<Integer> idSet = new HashSet<>();
        for (ItemPres item : materialPres){
            idSet.add(item.getId());
        }

        for (int i : idSet){
            ItemPres itemPres = null;
            for (ItemPres item : materialPres){
                if (item.getId() == i){
                    if (itemPres == null){
                        itemPres = item;
                    }else {
                        itemPres.addItemPress(item);
                    }
                }
            }
            if(itemPres != null){
                collapseList.add(itemPres);
            }
        }
        return  collapseList;
    }

}
