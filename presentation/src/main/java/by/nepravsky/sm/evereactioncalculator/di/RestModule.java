package by.nepravsky.sm.evereactioncalculator.di;

import javax.inject.Singleton;

import by.nepravsky.sm.data.net.repoimpl.PriceInfoRepoImp;
import by.nepravsky.sm.domain.repositories.PriceInfoRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class RestModule {

    @Provides
    @Singleton
    public PriceInfoRepository provideOrderRepository(PriceInfoRepoImp orderRepoImp){
        return orderRepoImp;
    }

}
