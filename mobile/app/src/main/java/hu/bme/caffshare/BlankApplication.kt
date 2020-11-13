package hu.bme.caffshare

import co.zsmb.rainbowcake.config.Loggers
import co.zsmb.rainbowcake.config.rainbowCake
import co.zsmb.rainbowcake.dagger.RainbowCakeApplication
import co.zsmb.rainbowcake.timber.TIMBER
import hu.bme.caffshare.di.AppComponent
import hu.bme.caffshare.di.ApplicationModule
import hu.bme.caffshare.di.DaggerAppComponent
import timber.log.Timber

class BlankApplication : RainbowCakeApplication() {

    override lateinit var injector: AppComponent

    override fun setupInjector() {
        injector = DaggerAppComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        rainbowCake {
            logger = Loggers.TIMBER
            isDebug = BuildConfig.DEBUG
        }

        Timber.plant(Timber.DebugTree())
    }

}
