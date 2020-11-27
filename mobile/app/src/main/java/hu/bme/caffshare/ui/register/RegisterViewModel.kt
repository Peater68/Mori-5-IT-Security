package hu.bme.caffshare.ui.register

import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val registerPresenter: RegisterPresenter
) : RainbowCakeViewModel<RegisterViewState>(Ready) {

    object RegistrationSuccessful : OneShotEvent
    object RegistrationFailed : OneShotEvent

    fun register(
        username: String,
        password: String,
        email: String,
        firstName: String,
        lastName: String
    ) = execute {
        val isRegistrationSuccessful = registerPresenter.register(
            username = username,
            password = password,
            email = email,
            firstName = firstName,
            lastName = lastName,
        )

        if (isRegistrationSuccessful) {
            postEvent(RegistrationSuccessful)
        } else {
            postEvent(RegistrationFailed)
        }
    }
}