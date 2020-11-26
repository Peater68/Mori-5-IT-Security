package hu.bme.caffshare.ui.profile

import co.zsmb.rainbowcake.withIOContext
import hu.bme.caffshare.domain.UserInteractor
import javax.inject.Inject

class ProfilePresenter @Inject constructor(
    private val userInteractor: UserInteractor
) {

    // TODO: modify returned date when merged with design
    suspend fun loadProfileData(): String? = withIOContext {
        userInteractor.getCurrentUserProfile()?.username
    }
}
