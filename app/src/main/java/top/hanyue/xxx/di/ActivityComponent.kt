package top.hanyue.xxx.di

import dagger.Component
import top.hanyue.xxx.ui.content.main.MainActivity

@ActivityScope
@Component(dependencies = [AppComponent::class])
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)
}