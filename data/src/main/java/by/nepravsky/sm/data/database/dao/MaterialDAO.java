package by.nepravsky.sm.data.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import by.nepravsky.sm.data.database.entity.MaterialDBE;
import io.reactivex.Flowable;

@Dao
public interface MaterialDAO {

    String TABLE_NAME = "items";

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE id IN (:idList)")
    Flowable<List<MaterialDBE>> getMaterialList(List<String> idList);


}
