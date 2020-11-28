package hu.bme.caffshare.ui.profile

import co.zsmb.rainbowcake.withIOContext
import hu.bme.caffshare.domain.AuthInteractor
import hu.bme.caffshare.ui.profile.model.ProfilePresenterModel
import javax.inject.Inject

class ProfilePresenter @Inject constructor(
    private val authInteractor: AuthInteractor
) {

    suspend fun loadProfileData(): ProfilePresenterModel? = withIOContext {
        ProfilePresenterModel(
            id = "id",
            firstName = "Bela",
            lastName = "Borsy",
            username = "bbalegnagyobbfaszu"
        )
    }

    suspend fun changePassword(oldPassword: String, newPassword: String) = withIOContext {
        authInteractor.changePassword(oldPassword, newPassword)
    }

    suspend fun deleteUser(): Unit = withIOContext {
        TODO()
    }

    suspend fun logout(): Unit = withIOContext {
        TODO()
    }

    suspend fun editProfile(profilePresenterModel: ProfilePresenterModel): Unit = withIOContext {
        TODO()
    }
}
