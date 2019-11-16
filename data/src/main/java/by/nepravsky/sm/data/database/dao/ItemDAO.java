package by.nepravsky.sm.data.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import by.nepravsky.sm.data.database.entity.ItemDBE;
import io.reactivex.Flowable;

@Dao
public interface ItemDAO {

    String TABLE_NAME = "items";

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE id IN (:idList)")
    Flowable<List<ItemDBE>> getItemList(List<String> idList);


}
