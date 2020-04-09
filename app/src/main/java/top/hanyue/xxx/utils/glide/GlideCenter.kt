package top.hanyue.xxx.utils.glide

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.MainThread
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions
import top.hanyue.xxx.BuildConfig
import top.hanyue.xxx.MyApplication
import java.io.File
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by Buzz on 2020/1/8.
 * Email :lmx2060918@126.com
 */
class GlideCenter {
    companion object {
        private var mInstance: GlideCenter? = null
        @MainThread
        fun get(): GlideCenter {
            if (mInstance == null) {
                mInstance = GlideCenter()
            }
            return mInstance!!
        }
    }

    init {
        MyApplication.appComponent?.inject(this)
    }

    @Inject
    @field:Named("default")
    lateinit var defaultBuilder: RequestBuilder<Drawable>

    @Inject
    @field:Named("withCircle")
    lateinit var circleBuilder: RequestBuilder<Drawable>

    @Inject
    @field:Named("withCrossFade")
    lateinit var crossFadeBuilder: RequestBuilder<Drawable>

    @Inject
    @field:Named("withCircleCrossFade")
    lateinit var circleCrossFadeBuilder: RequestBuilder<Drawable>


    fun showImage(imageView: ImageView?, url:Any?){
        if (imageView == null || url == null) return
        if (handleUrls(url) != null){
            defaultBuilder.load(url).into(imageView)
        }
    }

    fun showCircleImage(imageView: ImageView?, url:Any?){
        if (imageView == null || url == null) return
        if (handleUrls(url) != null){
            circleBuilder.load(url).into(imageView)
        }
    }

    fun showCrossFadeImage(imageView: ImageView?, url:Any?){
        if (imageView == null || url == null) return
        if (handleUrls(url) != null){
            crossFadeBuilder.load(url).into(imageView)
        }
    }

    fun showCircleCrossFadeImage(imageView: ImageView?, url:Any?){
        if (imageView == null || url == null) return
        if (handleUrls(url) != null){
            circleCrossFadeBuilder.load(url).into(imageView)
        }
    }

    private fun handleUrls(url:Any):Any?{
        if (url is String || url is Int || url is File){
            if (url is String) {
                if (url.endsWith("null")) {
                    return null
                }
                if (!url.startsWith("http")) {
                    return BuildConfig.DOMAIN_NAME + url
                }
            }
        }
        return url
    }
}