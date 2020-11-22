package hu.bme.caffshare.ui.profile

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val profilePresenter: ProfilePresenter
) : RainbowCakeViewModel<ProfileViewState>(Loading) {

    fun load() = execute {
        val userName = profilePresenter.loadProfileData()

        viewState = if (userName == null) {
            Error
        } else {
            ProfileContent(userName)
        }
    }
}