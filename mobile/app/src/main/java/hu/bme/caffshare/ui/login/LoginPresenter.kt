package hu.bme.caffshare.ui.login

import co.zsmb.rainbowcake.withIOContext
import javax.inject.Inject

class LoginPresenter @Inject constructor(
) {

    suspend fun login(username: String, password: String) = withIOContext {
        true
    }

}
