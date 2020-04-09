package top.hanyue.xxx.ui.content.main

import io.reactivex.disposables.CompositeDisposable
import top.hanyue.xxx.utils.network.ApiCenter
import top.hanyue.xxx.utils.network.RxSchedulers
import javax.inject.Inject

/**
 * Created by Buzz on 2019/12/17.
 * Email :lmx2060918@126.com
 */
class MainPresenter @Inject
constructor():MainContract.Presenter {
    private var mainView:MainContract.View? = null
    private val mDisposable = CompositeDisposable()

    override fun getMenu() {
        mDisposable.add(ApiCenter.get().resApi.test("header","qurey")
            .compose(RxSchedulers.threadIo())
            .compose(RxSchedulers.objectTransformer())
            .subscribe({ objectList ->
                mainView?.getMenuSuccess()
            },{
                mainView?.getMenuFailed()
            }))
    }

    override fun takeView(view: Any) {
        mainView = view as MainContract.View
    }

    override fun dropView() {
        mDisposable.clear()
        mainView = null
    }
}