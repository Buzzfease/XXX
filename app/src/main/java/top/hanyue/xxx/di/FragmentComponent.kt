package top.hanyue.xxx.di

import dagger.Component
import top.hanyue.xxx.ui.content.main.menu.MenuFragment

@FragmentScope
@Component(dependencies = [AppComponent::class])
interface FragmentComponent {
    fun inject(mineFragment: MenuFragment)
}