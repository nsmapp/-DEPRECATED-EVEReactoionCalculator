package by.nepravsky.sm.domain.entity;

import java.util.ArrayList;
import java.util.List;

public class Product implements DomainEntity{


    private long buildingTime;
    private List<FormulaComponent> componentsList = new ArrayList<>();
    private double materialSell;
    private double materialBuy;
    private double materialVol;

    public Product(long buildingTime, List<FormulaComponent> componentsList, double materialSell, double materialBuy, double materialVol) {
        this.buildingTime = buildingTime;
        this.componentsList = componentsList;
        this.materialSell = materialSell;
        this.materialBuy = materialBuy;
        this.materialVol = materialVol;
    }

    public double getMaterialVol() {
        return materialVol;
    }

    public double getMaterialSell() {
        return materialSell;
    }

    public double getMaterialBuy() {
        return materialBuy;
    }

    public long getBuildingTime() {
        return buildingTime;
    }

    public List<FormulaComponent> getComponentsList() {
        return componentsList;
    }
}
