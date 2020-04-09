package top.hanyue.xxx.utils.network

import androidx.annotation.MainThread
import top.hanyue.xxx.MyApplication
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by Buzz on 2019/7/1.
 * Email :lmx2060918@126.com
 */
class ApiCenter{
    companion object {
        private var mInstance: ApiCenter? = null
        @MainThread
        fun get(): ApiCenter {
            if (mInstance == null) {
                mInstance = ApiCenter()
            }
            return mInstance!!
        }
    }

    init {
        MyApplication.appComponent?.inject(this)
    }

    @Inject
    @field:Named("base")
    lateinit var resApi: ResApi

    @Inject
    @field:Named("8082")
    lateinit var resApi8082: ResApi

    @Inject
    @field:Named("8080")
    lateinit var resApi8080: ResApi
}