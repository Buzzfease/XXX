package top.hanyue.xxx

import android.app.Application
import top.hanyue.xxx.di.*
import top.hanyue.xxx.utils.EasyLog


/**
 * Created by Buzz on 2019/4/23.
 * Email :lmx2060918@126.com
 */
class MyApplication : Application() {

    companion object {
        var appComponent: AppComponent? = null
        private var instance: Application? = null
        fun  instance() = instance!!
    }

    init{
        initDependency()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        //开启关闭Log
        EasyLog.DEFAULT.enable = BuildConfig.showLog
        //捕获Crash,解决多任务栈程序崩溃后启动的问题
        CrashCollectHandler.instance.init(this)
    }

    private fun initDependency(){
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .httpModule(HttpModule())
                    .glideModule(GlideModule())
                    .build()
        }
    }
}