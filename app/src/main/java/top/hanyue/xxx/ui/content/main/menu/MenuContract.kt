package top.hanyue.xxx.ui.content.main.menu

import top.hanyue.xxx.ui.content.BasePresenter
import top.hanyue.xxx.ui.content.BaseView

/**
 * Created by Buzz on 2019/12/17.
 * Email :lmx2060918@126.com
 */
interface MenuContract {

    interface View: BaseView {
        fun getMenuSuccess()
        fun getMenuFailed()
    }

    interface Presenter: BasePresenter {
        fun getMenu()
    }
}