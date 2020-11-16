package hu.bme.caffshare.ui.login

import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginPresenter: LoginPresenter
) : RainbowCakeViewModel<LoginViewState>(Ready) {

    object LoginSuccessful : OneShotEvent
    object LoginFailed : OneShotEvent

    fun login(username: String, password: String) = execute {
        val loginSuccessful = loginPresenter.login(username, password)

        if (loginSuccessful) {
            postEvent(LoginSuccessful)
        } else {
            postEvent(LoginFailed)
        }
    }

}