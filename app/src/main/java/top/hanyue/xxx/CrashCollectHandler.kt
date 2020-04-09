package top.hanyue.xxx

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.os.Looper
import top.hanyue.xxx.utils.EasyLog
import top.hanyue.xxx.utils.EasyToast
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess


/**
 * Created by Buzz on 2019/7/5.
 * Email :lmx2060918@126.com
 */
class CrashCollectHandler : Thread.UncaughtExceptionHandler {
    private var mContext: Context? = null
    private var mDefaultHandler:Thread.UncaughtExceptionHandler ?= null
    // 用来存储设备信息和异常信息
    private val info = HashMap<String, String>()
    // 用于格式化日期,作为日志文件名的一部分
    @SuppressLint("SimpleDateFormat")
    private val formatter = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")

    companion object {
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CrashCollectHandler() }
    }

    fun init(pContext: Context) {
        this.mContext = pContext
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    //当UncaughtException发生时会转入该函数来处理
    override fun uncaughtException(t: Thread?, e: Throwable?) {
        if (!handleException(e) && mDefaultHandler != null){
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler?.uncaughtException(t,e)
        }else{
            try {
                //给Toast留出时间
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            //退出程序
            ActivityManager.finishAllActivity()
            android.os.Process.killProcess(android.os.Process.myPid())
            exitProcess(0)
        }
    }

    private fun handleException(ex: Throwable?):Boolean {
        if (ex == null){
            return false
        }
        Thread{
            Looper.prepare()
            EasyToast.newBuilder(R.layout.view_toast, R.id.tvToast).build().show(MyApplication.instance().getString(R.string.app_will_finish_because_error))
            Looper.loop()
        }.start()
        //收集设备参数信息
        collectDeviceInfo(MyApplication.instance())
        //保存日志文件
        saveCrashInfo2File(ex)
        return true
    }

    /**
     * 收集设备参数信息
     * @param ctx
     */
    private fun collectDeviceInfo(ctx: Context) {
        try {
            val pm = ctx.packageManager
            val pi = pm.getPackageInfo(ctx.packageName, PackageManager.GET_ACTIVITIES)

            if (pi != null) {
                val versionName = BuildConfig.VERSION_NAME
                val versionCode = BuildConfig.VERSION_CODE.toString()

                info["versionName"] = versionName
                info["versionCode"] = versionCode
            }
        } catch (e: PackageManager.NameNotFoundException) {
            EasyLog.DEFAULT.e( "an error occured when collect package info")
        }

        val fields = Build::class.java.declaredFields
        for (field in fields) {
            try {
                field.isAccessible = true
                info[field.name] = field.get(null).toString()
                EasyLog.DEFAULT.e(field.name + " : " + field.get(null))
            } catch (e: Exception) {
                EasyLog.DEFAULT.e("an error occured when collect crash info")
            }

        }
    }

    /**
     * 保存错误信息到文件中
     * *
     * @param ex
     * @return  返回文件名称,便于将文件传送到服务器
     */
    private fun saveCrashInfo2File(ex: Throwable): String? {
        val sb = StringBuffer()
        for (entry in info.entries) {
            val key = entry.key
            val value = entry.value
            sb.append("$key=$value\n")
        }

        val writer = StringWriter()
        val printWriter = PrintWriter(writer)
        ex.printStackTrace(printWriter)
        var cause: Throwable? = ex.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.close()

        val result = writer.toString()
        sb.append(result)
        try {
            val timestamp = System.currentTimeMillis()
            val time = formatter.format(Date())
            val fileName = "crash-$time-$timestamp.log"

            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                val path = MyApplication.instance().getExternalFilesDir(null).absolutePath.plus("/appCrashLog")
                val dir = File(path)
                if (!dir.exists()) {
                    dir.mkdirs()
                }
                val fos = FileOutputStream(path + fileName)
                fos.write(sb.toString().toByteArray())
                fos.close()
            }

            return fileName
        } catch (e: Exception) {
            EasyLog.DEFAULT.e("CrashHandler", "an error occured while writing file...")
        }
        return null
    }
}