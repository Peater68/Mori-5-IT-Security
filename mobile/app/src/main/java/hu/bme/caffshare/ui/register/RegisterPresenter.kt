package hu.bme.caffshare.ui.register

import co.zsmb.rainbowcake.withIOContext
import hu.bme.caffshare.domain.AuthInteractor
import javax.inject.Inject

class RegisterPresenter @Inject constructor(
    private val authInteractor: AuthInteractor
) {
    suspend fun register(
        username: String,
        password: String,
        email: String,
        firstName: String,
        lastName: String
    ) = withIOContext {
        authInteractor.register(
            username = username,
            password = password,
            email = email,
            firstName = firstName,
            lastName = lastName,
        )
        true
    }
}