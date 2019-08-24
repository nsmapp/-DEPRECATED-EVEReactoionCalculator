package by.nepravsky.sm.data.database;


import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import by.nepravsky.sm.data.database.dao.FormulaDAO;
import by.nepravsky.sm.data.database.dao.MaterialDAO;
import by.nepravsky.sm.data.database.entity.FormulaDBE;
import by.nepravsky.sm.data.database.entity.MaterialDBE;

@Database(entities = {FormulaDBE.class, MaterialDBE.class}, version = 11)
public abstract class AppDatabase extends RoomDatabase {


    private static AppDatabase appDatabase;

    public static AppDatabase getInstance(Context context){


        if(appDatabase == null){

            appDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class,
                    "reactions.dbsqlt")
                    .fallbackToDestructiveMigration()
                    .createFromAsset("reactions.dbsqlt")
                    .build();
            return appDatabase;
        }

        return appDatabase;
    }

    public abstract MaterialDAO getMaterialDAO();
    public abstract FormulaDAO getFormulaDAO();






}
