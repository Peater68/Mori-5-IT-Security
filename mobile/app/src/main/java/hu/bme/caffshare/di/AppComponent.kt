package hu.bme.caffshare.di

import co.zsmb.rainbowcake.dagger.RainbowCakeComponent
import co.zsmb.rainbowcake.dagger.RainbowCakeModule
import dagger.Component
import hu.bme.caffshare.data.network.CustomGlideModule
import hu.bme.caffshare.data.network.NetworkModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        RainbowCakeModule::class,
        ApplicationModule::class,
        ViewModelModule::class,
        NetworkModule::class
    ]
)
interface AppComponent : RainbowCakeComponent {
    fun inject(customGlideModule: CustomGlideModule)
}
