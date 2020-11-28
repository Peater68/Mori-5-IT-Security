package hu.bme.caffshare.ui.profile

import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.caffshare.ui.profile.model.ProfileUpdateData
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val profilePresenter: ProfilePresenter
) : RainbowCakeViewModel<ProfileViewState>(Loading) {

    object PasswordChangeSuccessful : OneShotEvent
    object PasswordChangeError : OneShotEvent

    object LoggedOut : OneShotEvent

    data class ShowEditProfileDialog(val profile: ProfileUpdateData) : OneShotEvent

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

    fun editProfile(profile: ProfileUpdateData) = execute {
        profilePresenter.editProfile(profile)

        viewState = Loading
        load()
    }

    fun logout() = execute {
        profilePresenter.logout()

        postEvent(LoggedOut)
    }

    fun showEditProfileDialog() {
        val state = viewState as? ProfileContent
        state?.let {
            val profileData = ProfileUpdateData(
                firstName = it.profile.firstName,
                lastName = it.profile.lastName,
                username = it.profile.username,
                email = it.profile.email,
            )

            postEvent(ShowEditProfileDialog(profileData))
        }
    }

}