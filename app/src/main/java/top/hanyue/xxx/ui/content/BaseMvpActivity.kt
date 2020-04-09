package top.hanyue.xxx.ui.content

import top.hanyue.xxx.MyApplication
import top.hanyue.xxx.di.ActivityComponent
import top.hanyue.xxx.di.DaggerActivityComponent
import javax.inject.Inject


/**
 * Created by Buzz on 2019/7/1.
 * Email :lmx2060918@126.com
 */
abstract class BaseMvpActivity<T: BasePresenter>:BaseActivity() {
    @Inject
    lateinit var presenter:T

    override fun onViewCreated(){
        initInject()
        presenter.takeView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView()
    }

    protected fun getActivityComponent(): ActivityComponent {
        return DaggerActivityComponent.builder()
                .appComponent(MyApplication.appComponent)
                .build()
    }

    protected abstract fun initInject()
}