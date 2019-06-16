package by.nepravsky.sm.data.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import by.nepravsky.sm.data.database.dao.StoryDAO;


@Entity(tableName = StoryDAO.TABLE_NAME)
public class StoryDBE {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "story")
    private String story;

    public StoryDBE(String story) {
        this.story = story;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }
}
