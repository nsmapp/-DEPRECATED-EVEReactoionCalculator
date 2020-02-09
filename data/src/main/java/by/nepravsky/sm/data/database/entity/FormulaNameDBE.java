package by.nepravsky.sm.data.database.entity;


import androidx.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

public class FormulaNameDBE {

    private int id;

    @SerializedName("en")
    @ColumnInfo(name = "en")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
