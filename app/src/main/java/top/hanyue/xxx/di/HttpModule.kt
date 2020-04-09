package top.hanyue.xxx.di

import android.app.Application
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import top.hanyue.xxx.BuildConfig
import top.hanyue.xxx.utils.network.ResApi
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Buzz on 2019/4/23.
 * Email :lmx2060918@126.com
 */
@Module
class HttpModule {

    companion object {
        const val DEFAULT_TIMEOUT = 10
    }

    @Singleton @Provides
    fun provideOkHttpCache(app: Application): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        val cacheFile = File(app.cacheDir, "cache")
        return Cache(cacheFile, cacheSize.toLong())
    }

    @Singleton @Provides
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor {
            chain: Interceptor.Chain? -> chain?.proceed(chain.request().newBuilder()
                .addHeader("Content-Type","application/json")
                .build())
        }
    }

    @Singleton @Provides
    fun provideOkHttpClient(headerInterceptor: Interceptor): OkHttpClient {
        val logInterceptor:HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.showLog) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
        val builder:OkHttpClient.Builder = OkHttpClient.Builder()
        return builder.readTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .addInterceptor(headerInterceptor)
                .addInterceptor(logInterceptor)
                .retryOnConnectionFailure(true).build()
    }

    /**
     * module返回两个相同的对象，因此使用到@Named注解。
     *
     * 注意：@Named注解在kotlin上有问题，在ResApi注入的地方，必须使用 @field:Named("base")，原因是当对
     * 属性或者主构造参数进行注解时，从相应的kotlin元素生成的Java元素不唯一（会有多个），因此在生成的
     * Java字节码中该注解有多个可能的位置，如果要精确指定该如何生成注解，请使用如下语法：
     * Class Example(@field T val field,   //Java 字段
     *               @get T val getter,    //Java getter
     *               @param T val param    //Java 构造函数参数)
     *
     * @see https://www.jianshu.com/p/1b98d0a0e42d
     * @see https://www.kotlincn.net/docs/reference/annotations.html
     * @see https://github.com/google/dagger/issues/848
     */
    @Singleton @Provides @Named("base")
    fun provideDefaultRemoteService(okHttpClient: OkHttpClient): ResApi {
        val retrofit:Retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BuildConfig.DOMAIN_NAME)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofit.create(ResApi::class.java)
    }


    @Singleton @Provides @Named("8082")
    fun provideRemoteServiceA(okHttpClient: OkHttpClient): ResApi {
        val retrofit:Retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BuildConfig.DOMAIN_NAME + ":8082")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofit.create(ResApi::class.java)
    }

    @Singleton @Provides @Named("8080")
    fun provideRemoteServiceB(okHttpClient: OkHttpClient): ResApi {
        val retrofit:Retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BuildConfig.DOMAIN_NAME + ":8080")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofit.create(ResApi::class.java)
    }
}
