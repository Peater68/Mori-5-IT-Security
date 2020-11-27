package hu.bme.caffshare.ui.profile

import co.zsmb.rainbowcake.withIOContext
import hu.bme.caffshare.ui.profile.model.ProfilePresenterModel
import javax.inject.Inject

class ProfilePresenter @Inject constructor(
) {

    suspend fun loadProfileData(): ProfilePresenterModel? = withIOContext {
        ProfilePresenterModel(
                id = "id",
                firstName = "Bela",
                lastName = "Borsy",
                username = "bbalegnagyobbfaszu"
        )
    }

    suspend fun changePassword(newPassword: ChangePasswordDialogFragment.NewPasswordWrapper): Unit = withIOContext {
        TODO("IDE JON A HÁLÓZATI HÍVÁS")
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
