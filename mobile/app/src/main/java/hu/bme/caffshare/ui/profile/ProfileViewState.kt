package hu.bme.caffshare.ui.profile

import hu.bme.caffshare.ui.profile.model.ProfilePresenterModel

sealed class ProfileViewState

object Loading : ProfileViewState()

object Error : ProfileViewState()

data class ProfileContent(
        var profile: ProfilePresenterModel
) : ProfileViewState()