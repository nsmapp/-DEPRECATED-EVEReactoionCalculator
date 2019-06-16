package by.nepravsky.sm.evereactioncalculator.di;

import javax.inject.Singleton;

import by.nepravsky.sm.evereactioncalculator.screens.main.MainViewModel;
import dagger.Component;

@Component(modules = {AppModule.class,
        DatabaseModule.class,
        RestModule.class,
        DomainModule.class})
@Singleton
public interface AppComponent {

    void runInject(MainViewModel mainViewModel);
}
