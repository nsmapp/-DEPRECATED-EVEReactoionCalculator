package by.nepravsky.sm.evereactioncalculator.di;

import javax.inject.Singleton;

import by.nepravsky.sm.data.net.repoimpl.OrderRepoImp;
import by.nepravsky.sm.domain.repositories.OrderRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class RestModule {

    @Provides
    @Singleton
    public OrderRepository provideOrderRepository(OrderRepoImp orderRepoImp){
        return orderRepoImp;
    }

}
