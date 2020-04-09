package top.hanyue.xxx.di

import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import top.hanyue.xxx.MyApplication
import top.hanyue.xxx.R
import top.hanyue.xxx.utils.glide.GlideCircleTransform
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Buzz on 2020/1/8.
 * Email :lmx2060918@126.com
 */
@Module
class GlideModule {

    @Singleton
    @Provides
    @Named("basic")
    fun provideRequestOptions(): RequestOptions{
        return RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
    }

    @Singleton
    @Provides
    @Named("placeHolder")
    fun providePlaceHolder(@Named("basic") options: RequestOptions): RequestOptions{
        return options.placeholder(R.mipmap.ic_launcher)
    }

    @Singleton
    @Provides
    @Named("errorRes")
    fun provideErrorRes(@Named("placeHolder") options: RequestOptions): RequestOptions{
        return options.error(R.mipmap.ic_launcher)
    }

    @Singleton
    @Provides
    @Named("default")
    fun provideRequestBuilder(@Named("errorRes") options: RequestOptions): RequestBuilder<Drawable>{
        return Glide.with(MyApplication.instance())
            .asDrawable()
            .apply(options)
    }

    @Singleton
    @Provides
    @Named("withCrossFade")
    fun provideCrossFade(@Named("errorRes") options: RequestOptions): RequestBuilder<Drawable>{
        return Glide.with(MyApplication.instance())
            .asDrawable()
            .apply(options)
            .transition(DrawableTransitionOptions.withCrossFade())
    }

    @Singleton
    @Provides
    @Named("withCircle")
    fun provideCircle(@Named("errorRes") options: RequestOptions): RequestBuilder<Drawable>{
        return Glide.with(MyApplication.instance())
            .asDrawable()
            .apply(options)
            .apply(RequestOptions.bitmapTransform(GlideCircleTransform()))
    }

    @Singleton
    @Provides
    @Named("withCircleCrossFade")
    fun provideCircleCrossFade(@Named("errorRes") options: RequestOptions): RequestBuilder<Drawable>{
        return Glide.with(MyApplication.instance())
            .asDrawable()
            .apply(options)
            .apply(RequestOptions.bitmapTransform(GlideCircleTransform()))
            .transition(DrawableTransitionOptions.withCrossFade())
    }

}