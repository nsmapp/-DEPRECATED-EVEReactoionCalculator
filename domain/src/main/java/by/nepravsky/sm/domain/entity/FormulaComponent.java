package by.nepravsky.sm.domain.entity;

public class FormulaComponent implements DomainEntity {

    private String id;
    private double quantity = 0;
    private boolean isProduct;

    private double vol = 0;
    private String name = "no name";

    private double buy;
    private double sell;

    private long runs;


    public FormulaComponent(String id, double quantity, boolean isProduct) {
        this.id = id;
        this.quantity = quantity;
        this.isProduct = isProduct;
    }

    public void setMaterialInfoAndPrice(double vol, String name, double buy, double sell, long runs) {
        this.vol = vol;
        this.name = name;
        this.buy = buy;
        this.sell = sell;
        this.runs = runs;
    }


    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getVol() {
        return vol;
    }

    public String getName() {
        return name;
    }

    public double getBuy() {
        return buy;
    }

    public double getSell() {
        return sell;
    }

    public String getId() {
        return id;
    }

    public double getQuantity() {
        return quantity;
    }

    public boolean isProduct() {
        return isProduct;
    }
}
