package by.nepravsky.sm.data.database.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import by.nepravsky.sm.data.database.entity.StoryDBE;
import io.reactivex.Flowable;
import io.reactivex.Observable;

@Dao
public interface StoryDAO {

    String TABLE_NAME = "stories";

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(StoryDBE storyDBE);

    @Query("SELECT * FROM " + TABLE_NAME)
    Flowable<List<StoryDBE>> getAll();

}
