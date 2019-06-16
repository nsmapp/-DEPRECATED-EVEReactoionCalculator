package by.nepravsky.sm.evereactioncalculator.di;


import android.content.Context;

import javax.inject.Singleton;

import by.nepravsky.sm.data.database.AppDatabase;
import by.nepravsky.sm.data.database.repositoryImpl.FormulaRepoImpl;
import by.nepravsky.sm.data.database.repositoryImpl.MaterialRepoImpl;
import by.nepravsky.sm.domain.repositories.FormulaRepositories;
import by.nepravsky.sm.domain.repositories.MaterialRepositories;
import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    private Context context;

    public DatabaseModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public AppDatabase provideDatabase(){
        return AppDatabase.getInstance(context);
    }

    @Provides
    public FormulaRepositories provideFormulaRepo(FormulaRepoImpl formulaRepoImpl){
        return formulaRepoImpl;
    }

    @Provides
    public MaterialRepositories provideMaterialRepo(MaterialRepoImpl materialRepoImpl){
        return materialRepoImpl;
    }
}
