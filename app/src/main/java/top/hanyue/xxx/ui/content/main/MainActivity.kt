package top.hanyue.xxx.ui.content.main

import kotlinx.android.synthetic.main.activity_main.*
import top.hanyue.xxx.R
import top.hanyue.xxx.ui.content.BaseMvpActivity
import top.hanyue.xxx.utils.EasyLog
import top.hanyue.xxx.utils.EasyStatusBar
import top.hanyue.xxx.utils.glide.GlideCenter

/**
 * Created by Buzz on 2019/12/17.
 * Email :lmx2060918@126.com
 */
class MainActivity : BaseMvpActivity<MainPresenter>(), MainContract.View{

    override fun initInject() {
        getActivityComponent().inject(this)
    }

    override fun layoutResID(): Int {
        return R.layout.activity_main
    }

    override fun initViewAndEvent() {
        EasyStatusBar.makeStatusBarTransparent(this, true, rlMain, avatar)
        showLoadingDialog()
        GlideCenter.get().showCircleImage(avatar, R.mipmap.ic_avatar)
        GlideCenter.get().showCrossFadeImage(pic, R.mipmap.ic_load_circle)
        presenter.getMenu()
    }

    override fun getMenuSuccess() {
        EasyLog.DEFAULT.e("success")
        hideLoadingDialog()
        toast("success")
    }

    override fun getMenuFailed() {
        EasyLog.DEFAULT.e("failed")
        hideLoadingDialog()
        toast("failed")
    }

}