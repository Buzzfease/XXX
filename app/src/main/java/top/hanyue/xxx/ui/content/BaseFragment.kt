package top.hanyue.xxx.ui.content

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trello.rxlifecycle2.components.support.RxFragment
import top.hanyue.xxx.R
import top.hanyue.xxx.utils.DialogFactory
import top.hanyue.xxx.utils.EasyToast

/**
 * Created by Buzz on 2019/4/23.
 * Email :lmx2060918@126.com
 */
abstract class BaseFragment : RxFragment() {
    private lateinit var mView: View
    private var mContext:Context? = null
    private var loadingDialog: Dialog? = null
    private var isViewCreated:Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        beforeOnCreate()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (layoutResID() != 0) {
            mView = inflater.inflate(layoutResID(), container, false)
            mView
        } else {
            super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewCreated = true
        initEventView()
    }

    abstract fun beforeOnCreate()

    abstract fun initEventView()

    abstract fun layoutResID(): Int

    fun toast(content: String?) {
        if (!content.isNullOrEmpty()){
            EasyToast.newBuilder(R.layout.view_toast, R.id.tvToast).build().show(content)
        }
    }

    fun showLoadingDialog() {
        if (activity != null){
            loadingDialog = DialogFactory.getLoadingDialog(activity!!, getString(R.string.please_wait))
        }
    }

    fun showLoadingDialog(str: String?) {
        if (activity != null){
            loadingDialog = DialogFactory.getLoadingDialog(activity!!, str)
        }
    }

    fun finishActivity(){
        if (activity != null){
            activity?.finish()
        }
    }

    fun hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog!!.isShowing){
            loadingDialog?.dismiss()
        }
    }

    override fun onDestroy() {
        loadingDialog?.dismiss()
        loadingDialog = null
        super.onDestroy()
    }
}