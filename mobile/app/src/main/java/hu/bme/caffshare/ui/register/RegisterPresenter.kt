package hu.bme.caffshare.ui.register

import co.zsmb.rainbowcake.withIOContext
import javax.inject.Inject

class RegisterPresenter @Inject constructor(
) {
    suspend fun register(username: String, password: String) = withIOContext {
        true
    }
}