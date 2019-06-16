package by.nepravsky.sm.evereactioncalculator.app;

import android.app.Application;
import by.nepravsky.sm.evereactioncalculator.di.AppComponent;
import by.nepravsky.sm.evereactioncalculator.di.AppModule;
import by.nepravsky.sm.evereactioncalculator.di.DaggerAppComponent;
import by.nepravsky.sm.evereactioncalculator.di.DatabaseModule;
import by.nepravsky.sm.evereactioncalculator.di.DomainModule;
import by.nepravsky.sm.evereactioncalculator.di.RestModule;

public class App extends Application{

    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(getApplicationContext()))
                .databaseModule(new DatabaseModule(getApplicationContext()))
                .restModule(new RestModule())
                .domainModule(new DomainModule())
                .build();
    }
}
