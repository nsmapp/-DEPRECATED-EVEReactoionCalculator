package by.nepravsky.sm.data.net;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import by.nepravsky.sm.data.net.entity.MaterialOrder;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Singleton
public class RestModule {

    private Gson gson;
    private RestApi restApi;

    @Inject
    public RestModule() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(
//                new HttpLoggingInterceptor.Logger() {
//                    @Override
//                    public void log(String message) {
//                          Log.d("logd interceptor", message);
//                    }
//                }
        );

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

        gson = new GsonBuilder()
                .setLenient()
                .create();

        this.restApi = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://esi.evetech.net/")
                .client(okHttpClient)
                .build()
                .create(RestApi.class);
    }

    public Observable<List<MaterialOrder>> getMarketOrder(String typeId, int regionId){
        return restApi.getOrders(regionId, typeId);
    }

}
