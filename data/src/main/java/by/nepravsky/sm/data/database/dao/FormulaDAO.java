package by.nepravsky.sm.data.database.dao;


import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import by.nepravsky.sm.data.database.entity.FormulaDBE;
import by.nepravsky.sm.data.database.entity.FormulaNameDBE;
import io.reactivex.Single;

@Dao
public interface FormulaDAO {

    String TABLE_NAME = "formula";

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE en LIKE :name")
    Single<FormulaDBE> getFormuls(String name);

    @Query("SELECT * FROM " + TABLE_NAME)
    Single<List<FormulaDBE>> getAllFormuls();

    @Query("SELECT id, en FROM " + TABLE_NAME + " WHERE type = 2 ORDER BY en")
    Single<List<FormulaNameDBE>> getBoostersNames();

    @Query("SELECT id, en FROM " + TABLE_NAME + " WHERE type = 3 ORDER BY en")
    Single<List<FormulaNameDBE>> getFulleriteNames();

    @Query("SELECT id, en FROM " + TABLE_NAME + " WHERE type = 1 ORDER BY en")
    Single<List<FormulaNameDBE>> getCompositeNames();

}
