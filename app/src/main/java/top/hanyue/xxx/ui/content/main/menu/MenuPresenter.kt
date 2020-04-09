package top.hanyue.xxx.ui.content.main.menu

import io.reactivex.disposables.CompositeDisposable
import top.hanyue.xxx.utils.network.ApiCenter
import top.hanyue.xxx.utils.network.RxSchedulers
import top.hanyue.xxx.ui.content.main.MainContract
import javax.inject.Inject

/**
 * Created by Buzz on 2019/12/17.
 * Email :lmx2060918@126.com
 */
class MenuPresenter @Inject
constructor():MainContract.Presenter{
    private var menuView: MenuContract.View? = null
    private val mDisposable = CompositeDisposable()

    override fun getMenu() {
        mDisposable.add(
            ApiCenter.get().resApi.test("header","qurey")
                .compose(RxSchedulers.threadIo())
                .compose(RxSchedulers.objectTransformer())
                .subscribe({ objectList ->
                    menuView?.getMenuSuccess()
                },{
                    menuView?.getMenuFailed()
                }))    }

    override fun takeView(view: Any) {
        menuView = view as MenuContract.View
    }

    override fun dropView() {
        mDisposable.clear()
        menuView = null
    }
}