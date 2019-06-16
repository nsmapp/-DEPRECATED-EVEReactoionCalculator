package by.nepravsky.sm.domain.entity;

import java.util.ArrayList;
import java.util.List;

public class Formula {

    private List<FormulaComponent> materialList;
    private List<FormulaComponent> productList;
    private int time;
    private List<String> IdList = new ArrayList<>();

    public Formula(List<FormulaComponent> materialList, List<FormulaComponent> productList, int time) {
        this.materialList = materialList;
        this.productList = productList;
        this.time = time;

        for (FormulaComponent fc: materialList) {
            IdList.add(fc.getId());
        }

        for (FormulaComponent fc: productList){
            IdList.add(fc.getId());
        }


    }

    public List<String> getIdList() {
        return IdList;
    }

    public int getTime() {
        return time;
    }

    public List<FormulaComponent> getMaterialList() {
        return materialList;
    }

    public List<FormulaComponent> getProductList() {
        return productList;
    }
}
