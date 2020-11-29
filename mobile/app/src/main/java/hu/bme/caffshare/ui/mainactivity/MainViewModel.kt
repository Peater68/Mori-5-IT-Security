package hu.bme.caffshare.ui.mainactivity

import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.caffshare.domain.model.DomainRole
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val mainPresenter: MainPresenter
) : RainbowCakeViewModel<MainViewState>(MainActivityReady) {

    object LoginNeeded : OneShotEvent
    data class UserLoggedIn(val role: DomainRole) : OneShotEvent

    fun load() = execute {
        val userRole = mainPresenter.load()
        if (userRole == null) {
            postEvent(LoginNeeded)
        } else {
            postEvent(UserLoggedIn(userRole))
        }
    }

}
