package top.hanyue.xxx.di

import dagger.Component
import top.hanyue.xxx.utils.glide.GlideCenter
import top.hanyue.xxx.utils.network.ApiCenter
import javax.inject.Singleton

@Singleton
@Component(modules = [HttpModule::class, GlideModule::class])
interface AppComponent {
    fun inject(apiCenter: ApiCenter)
    fun inject(glideCenter: GlideCenter)
}