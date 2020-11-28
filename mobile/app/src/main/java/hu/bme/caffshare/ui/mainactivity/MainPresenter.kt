package hu.bme.caffshare.ui.mainactivity

import co.zsmb.rainbowcake.withIOContext
import hu.bme.caffshare.domain.UserInteractor
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val userInteractor: UserInteractor
) {
    suspend fun load() = withIOContext {
        userInteractor.getCurrentUserRole()
    }
}
