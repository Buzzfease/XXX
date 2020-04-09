package top.hanyue.xxx

import android.app.Activity
import java.util.*

object ActivityManager {
    private var activityStack: Stack<Activity>? = null// activity栈

    // 获取栈顶的activity，先进后出原则
    fun getLastActivity(): Activity? {
        return activityStack?.lastElement()
    }


    // 把一个activity压入栈中
    fun pushActivity(activity: Activity) {
        if (activityStack == null) {
            activityStack = Stack()
        }
        activityStack!!.add(activity)
    }

    // 移除一个activity
    fun popActivity(activity: Activity) {
        if (activityStack != null && activityStack!!.size > 0) {
            activity.finish()
            activityStack!!.remove(activity)
        }
    }

    // 退出所有activity
    fun finishAllActivity() {
        if (activityStack != null) {
            while (activityStack?.size!! > 0) {
                val activity = getLastActivity() ?: break
                popActivity(activity)
            }
        }
    }
}