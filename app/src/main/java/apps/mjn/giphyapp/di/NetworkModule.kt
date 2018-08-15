package apps.mjn.giphyapp.di

import apps.mjn.giphyapp.Constants
import apps.mjn.giphyapp.network.ApiInterface
import com.bumptech.glide.load.model.stream.HttpGlideUrlLoader.TIMEOUT
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by mJafarinejad on 8/11/2018.
 */

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun getOkHttpClient(): OkHttpClient {
        val timeout = 15
        return OkHttpClient.Builder()
                .readTimeout(timeout.toLong(), TimeUnit.SECONDS)
                .writeTimeout(timeout.toLong(), TimeUnit.SECONDS)
                .connectTimeout(timeout.toLong(), TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create(GsonBuilder()
            .disableHtmlEscaping()
            .serializeNulls()
            .serializeSpecialFloatingPointValues()
            .create())

    @Provides
    @Singleton
    fun provideGson() = Gson()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory): Retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

    @Provides
    @Singleton
    fun provideApiInterface(retrofit: Retrofit): ApiInterface = retrofit.create<ApiInterface>(ApiInterface::class.java)
}