package by.nepravsky.sm.data.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import by.nepravsky.sm.data.database.dao.FormulaDAO;


@Entity(tableName = FormulaDAO.TABLE_NAME)
public class FormulaDBE {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private  int id;

    @ColumnInfo(name = "type")
    private int type = 0;

    @NonNull
    @ColumnInfo(name = "ru")
    private String ru = "";

    @NonNull
    @ColumnInfo(name = "en")
    private String en = "";

    @NonNull
    @ColumnInfo(name = "fr")
    private String fr = "";

    @NonNull
    @ColumnInfo(name = "de")
    private String de = "";

    @NonNull
    @ColumnInfo(name = "mat")
    private String material = "";

    @ColumnInfo(name = "prod")
    private String product;

    @NonNull
    @ColumnInfo(name = "time")
    private String time = "";

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getRu() {
        return ru;
    }

    public void setRu(String ru) {
        this.ru = ru;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getFr() {
        return fr;
    }

    public void setFr(String fr) {
        this.fr = fr;
    }

    public String getDe() {
        return de;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

