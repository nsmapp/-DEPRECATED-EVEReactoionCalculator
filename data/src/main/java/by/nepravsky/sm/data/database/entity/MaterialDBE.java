package by.nepravsky.sm.data.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import by.nepravsky.sm.data.database.dao.MaterialDAO;

@Entity(tableName = MaterialDAO.TABLE_NAME)
public class MaterialDBE {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private  String id ="";

    @ColumnInfo(name = "ru")
    private String ru;

    @ColumnInfo(name = "en")
    private String en;

    @ColumnInfo(name = "fr")
    private String fr;

    @ColumnInfo(name = "de")
    private String de;

    @ColumnInfo(name = "volume")
    private double volume;

    @ColumnInfo(name = "base_price")
    private double basePrice;

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getRu() {
        return ru;
    }

    public String getEn() {
        return en;
    }

    public String getFr() {
        return fr;
    }

    public String getDe() {
        return de;
    }

    public double getVolume() {
        return volume;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setRu(String ru) {
        this.ru = ru;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public void setFr(String fr) {
        this.fr = fr;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }
}
