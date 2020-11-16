package hu.bme.caffshare.ui.profile

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val profilePresenter: ProfilePresenter
) : RainbowCakeViewModel<ProfileViewState>(Ready) {

    fun load() = execute {
        profilePresenter.loadProfileData()
    }
}