package top.hanyue.xxx.ui.content.main.menu
import top.hanyue.xxx.R
import top.hanyue.xxx.ui.content.BaseMvpFragment

/**
 * Created by Buzz on 2019/12/17.
 * Email :lmx2060918@126.com
 */
class MenuFragment : BaseMvpFragment<MenuPresenter>(), MenuContract.View {

    override fun initInject() {
        getFragmentComponent().inject(this)
    }

    override fun layoutResID(): Int {
        return R.layout.fragment_menu
    }

    override fun beforeOnCreate() {
    }

    override fun initEventView() {
        showLoadingDialog()
        presenter.getMenu()
    }

    override fun getMenuSuccess() {
        hideLoadingDialog()
        toast("success")
    }

    override fun getMenuFailed() {
        hideLoadingDialog()
        toast("failed")
    }
}