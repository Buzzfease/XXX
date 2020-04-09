package top.hanyue.xxx.utils

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat

/**
 * Created by Buzz on 2020/1/9.
 * Email :lmx2060918@126.com
 */
object EasyStatusBar {

     fun makeStatusBarTransparent(activity: AppCompatActivity, isLightBg:Boolean, container: View, vararg content: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isLightBg) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        activity.window.statusBarColor = Color.TRANSPARENT
        //适配刘海
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val lp = activity.window.attributes
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            activity.window.attributes = lp
        }
 //            //华为机型
 //            if (Build.MANUFACTURER.equals("HUAWEI")){
 //                removeDarkStatusBar(activity);
 //            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(container) { view, windowInsetsCompat ->
            for (value in content) {
                setMarginTop(value, windowInsetsCompat.systemWindowInsetTop)
            }
            windowInsetsCompat.consumeSystemWindowInsets()
        }
     }

     fun setStatusBarColor(activity: AppCompatActivity, barColor: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (barColor != -1) {
                activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                activity.window.statusBarColor = barColor
            }
            //适配刘海
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val lp = activity.window.attributes
                lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER
                activity.window.attributes = lp
            }
        }
     }

    private fun setMarginTop(view: View, marginTop:Int) {
        val menuLayoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
        menuLayoutParams.setMargins(0, marginTop, 0, 0)
        view.layoutParams = menuLayoutParams
    }

    private fun removeDarkStatusBar(activity: AppCompatActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                val decorViewClazz = Class.forName("com.android.internal.policy.DecorView")
                val field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor")
                field.isAccessible = true
                field.setInt(activity.window.decorView, Color.TRANSPARENT)  //改为透明
            }
            catch (e:Exception) {
                e.printStackTrace()
            }

        }
    }
}