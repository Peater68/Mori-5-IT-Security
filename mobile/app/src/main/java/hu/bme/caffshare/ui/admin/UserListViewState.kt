package hu.bme.caffshare.ui.admin

import hu.bme.caffshare.ui.admin.model.User

sealed class UserListViewState

object Loading : UserListViewState()

object Error : UserListViewState()

object Empty : UserListViewState()

data class UserListContent(val users: List<User> = emptyList()) : UserListViewState()
