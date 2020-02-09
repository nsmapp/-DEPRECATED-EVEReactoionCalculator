package by.nepravsky.sm.data.database;


import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import by.nepravsky.sm.data.database.dao.FormulaDAO;
import by.nepravsky.sm.data.database.dao.ItemDAO;
import by.nepravsky.sm.data.database.entity.FormulaDBE;
import by.nepravsky.sm.data.database.entity.FormulaNameDBE;
import by.nepravsky.sm.data.database.entity.ItemDBE;

@Database(entities = {FormulaDBE.class, ItemDBE.class }, version = 14)
public abstract class AppDatabase extends RoomDatabase {


    private static AppDatabase appDatabase;

    public static AppDatabase getInstance(Context context){


        if(appDatabase == null){

            appDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class,
                    "reactions")
                    .fallbackToDestructiveMigration()
                    .createFromAsset("reactions")
                    .build();
            return appDatabase;
        }

        return appDatabase;
    }

    public abstract ItemDAO getItemDAO();
    public abstract FormulaDAO getFormulaDAO();






}
