package top.hanyue.xxx.utils

import android.app.Activity
import android.app.Dialog
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import top.hanyue.xxx.MyApplication
import top.hanyue.xxx.R

object DialogFactory {

    fun getLoadingDialog(activity: Activity, msg: String? = MyApplication.instance().getString(R.string.please_wait)): Dialog {
        val dialog = Dialog(activity, R.style.LoadingDialog)
        val contentView = LayoutInflater.from(activity).inflate(R.layout.view_dialog_loading, null)
        val aniImage = contentView.findViewById(R.id.ivLoading) as ImageView
        val msgView = contentView.findViewById(R.id.tvMessage) as TextView
        val ani = AnimationUtils.loadAnimation(activity, R.anim.loading_ani)
        aniImage.startAnimation(ani)

        if (TextUtils.isEmpty(msg)) {
            msgView.visibility = View.GONE
        } else {
            msgView.visibility = View.VISIBLE
            msgView.text = msg
        }
        dialog.setCancelable(false)
        dialog.setContentView(contentView)
        dialog.show()
        return dialog
    }
}