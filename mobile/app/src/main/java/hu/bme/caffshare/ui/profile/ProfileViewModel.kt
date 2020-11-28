package hu.bme.caffshare.ui.profile

import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.caffshare.ui.profile.model.ProfilePresenterModel
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val profilePresenter: ProfilePresenter
) : RainbowCakeViewModel<ProfileViewState>(Loading) {

    object PasswordChangeSuccessful : OneShotEvent
    object PasswordChangeError : OneShotEvent

    object LoggedOut : OneShotEvent

    fun load() = execute {
        val profile = profilePresenter.loadProfileData()

        viewState = if (profile == null) {
            Error
        } else {
            ProfileContent(profile)
        }
    }

    fun changePassword(oldPassword: String, newPassword: String) = execute {
        val isPasswordChangeSuccessful = profilePresenter.changePassword(oldPassword, newPassword)

        if (isPasswordChangeSuccessful) {
            postEvent(PasswordChangeSuccessful)
        } else {
            postEvent(PasswordChangeError)
        }
    }

    fun editProfile(profile: ProfilePresenterModel) = execute {
        profilePresenter.editProfile(profilePresenterModel = profile)

        val newProfile = profilePresenter.loadProfileData()

        viewState = if (newProfile == null) {
            Error
        } else {
            ProfileContent(
                newProfile
            )
        }
    }

    fun logout() = execute {
        profilePresenter.logout()

        postEvent(LoggedOut)
    }

}