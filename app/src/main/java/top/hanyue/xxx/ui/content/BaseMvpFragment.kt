package top.hanyue.xxx.ui.content

import android.os.Bundle
import android.view.View
import top.hanyue.xxx.MyApplication
import top.hanyue.xxx.di.DaggerFragmentComponent
import top.hanyue.xxx.di.FragmentComponent

import javax.inject.Inject


/**
 * Created by Buzz on 2019/7/1.
 * Email :lmx2060918@126.com
 */
abstract class BaseMvpFragment<T: BasePresenter>: BaseFragment() {
    @Inject
    lateinit var presenter:T

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initInject()
        presenter.takeView(this)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.dropView()
    }

    protected fun getFragmentComponent(): FragmentComponent {
        return DaggerFragmentComponent.builder()
                .appComponent(MyApplication.appComponent)
                .build()
    }

    abstract fun initInject()
}