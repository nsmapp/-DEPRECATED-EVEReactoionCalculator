package by.nepravsky.sm.data.database.dao;


import androidx.room.Dao;
import androidx.room.Query;

import by.nepravsky.sm.data.database.entity.FormulaDBE;
import io.reactivex.Single;

@Dao
public interface FormulaDAO {

    String TABLE_NAME = "formula";

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE en LIKE :name")
    Single<FormulaDBE> getFormuls(String name);


}
