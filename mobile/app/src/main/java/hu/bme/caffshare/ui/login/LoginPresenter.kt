package hu.bme.caffshare.ui.login

import co.zsmb.rainbowcake.withIOContext
import hu.bme.caffshare.domain.AuthInteractor
import javax.inject.Inject

class LoginPresenter @Inject constructor(
    private val authInteractor: AuthInteractor
) {

    suspend fun login(username: String, password: String) = withIOContext {
        authInteractor.login(username, password)
    }

}
