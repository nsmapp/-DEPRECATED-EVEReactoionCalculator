package by.nepravsky.sm.evereactioncalculator.di;

import android.content.Context;
import android.content.res.Resources;

import javax.inject.Singleton;

import by.nepravsky.sm.domain.executors.PostExecutionThread;
import by.nepravsky.sm.evereactioncalculator.executors.UIThread;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule  {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public Context provideContext(){
        return context;
    }

    @Provides
    @Singleton
    public  PostExecutionThread provideUIThread(UIThread uiExecutionThread){
        return uiExecutionThread;
    }

    @Provides
    @Singleton
    public Resources providesResources() {
        return context.getResources();
    }



}
