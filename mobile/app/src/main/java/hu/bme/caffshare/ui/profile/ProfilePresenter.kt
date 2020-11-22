package hu.bme.caffshare.ui.profile

import co.zsmb.rainbowcake.withIOContext
import javax.inject.Inject

class ProfilePresenter @Inject constructor(
) {

    suspend fun loadProfileData(): String? = withIOContext {
        "Borsy Pres Béla"
    }
}
