package hu.bme.caffshare.ui.profile

sealed class ProfileViewState

object Loading : ProfileViewState()

object Error : ProfileViewState()

data class ProfileContent(val username: String) : ProfileViewState()