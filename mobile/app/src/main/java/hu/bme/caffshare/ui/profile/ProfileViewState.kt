package hu.bme.caffshare.ui.profile

import hu.bme.caffshare.ui.profile.model.User

sealed class ProfileViewState

object Loading : ProfileViewState()

object Error : ProfileViewState()

data class ProfileContent(
    var profile: User
) : ProfileViewState()