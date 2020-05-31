package barissaglam.client.wallpaperapp.di.modules

import android.content.Context
import barissaglam.client.wallpaperapp.BuildConfig
import barissaglam.client.wallpaperapp.base.di.ApiInterceptor
import barissaglam.client.wallpaperapp.data.remote.service.RemoteApiService
import barissaglam.client.wallpaperapp.util.DateDeserializer
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class NetworkModule {

    @get:Provides
    @get:Singleton
    val provideRetrofitBuilder: Retrofit.Builder = Retrofit.Builder()

    @Provides
    @Singleton
    fun provideOkHttpClientBuilder(context: Context): OkHttpClient.Builder {
        val cacheSize = (10 * 1024 * 1024).toLong()

        return OkHttpClient.Builder()
            .readTimeout(15.toLong(), TimeUnit.SECONDS)
            .connectTimeout(15.toLong(), TimeUnit.SECONDS)
            .cache(Cache(context.cacheDir, cacheSize))
    }

    @get:Provides
    @get:Singleton
    val provideConverterFactory: Converter.Factory = GsonConverterFactory.create(GsonBuilder().registerTypeAdapter(Date::class.java, DateDeserializer()).create())


    @Provides
    @Singleton
    fun provideApi(context: Context, builder: Retrofit.Builder, okHttpClientBuilder: OkHttpClient.Builder, converterFactory: Converter.Factory, interceptor: ApiInterceptor): RemoteApiService {
        if (BuildConfig.DEBUG) {
            okHttpClientBuilder.addNetworkInterceptor(StethoInterceptor())
            okHttpClientBuilder.addInterceptor(ChuckInterceptor(context))
        }
        okHttpClientBuilder.addInterceptor(interceptor)
        val client = okHttpClientBuilder.build()
        return builder.client(client)
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(nullOnEmptyConverterFactory)
            .addConverterFactory(converterFactory)
            .build()
            .create(RemoteApiService::class.java)
    }

    private val nullOnEmptyConverterFactory = object : Converter.Factory() {
        fun converterFactory() = this
        override fun responseBodyConverter(type: Type, annotations: Array<out Annotation>, retrofit: Retrofit) =
            object : Converter<ResponseBody, Any?> {
                val nextResponseBodyConverter = retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations)

                override fun convert(value: ResponseBody) =
                    if (value.contentLength() != 0L) nextResponseBodyConverter.convert(value) else null
            }
    }


}
