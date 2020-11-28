package hu.bme.caffshare.ui.profile

import co.zsmb.rainbowcake.withIOContext
import hu.bme.caffshare.domain.AuthInteractor
import hu.bme.caffshare.domain.UserInteractor
import hu.bme.caffshare.ui.profile.model.ProfileUpdateData
import hu.bme.caffshare.ui.profile.model.User
import hu.bme.caffshare.ui.profile.model.toUIModel
import javax.inject.Inject

class ProfilePresenter @Inject constructor(
    private val authInteractor: AuthInteractor,
    private val userInteractor: UserInteractor
) {

    suspend fun loadProfileData(): User? = withIOContext {
        userInteractor.getCurrentUserProfile()?.toUIModel()
    }

    suspend fun changePassword(oldPassword: String, newPassword: String) = withIOContext {
        authInteractor.changePassword(oldPassword, newPassword)
    }

    suspend fun logout() = withIOContext {
        authInteractor.logout()
    }

    suspend fun editProfile(user: ProfileUpdateData) = withIOContext {
        userInteractor.updateCurrentUser(
            user.firstName,
            user.lastName,
            user.username,
            user.email
        )
    }
}
