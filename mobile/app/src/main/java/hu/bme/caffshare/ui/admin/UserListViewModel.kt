package hu.bme.caffshare.ui.admin

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import javax.inject.Inject

class UserListViewModel @Inject constructor(
    private val userListPresenter: UserListPresenter
) : RainbowCakeViewModel<UserListViewState>(Loading) {

    fun load() = execute {
        val usersList = userListPresenter.getUsers()

        viewState = when {
            usersList == null -> {
                Error
            }
            usersList.isEmpty() -> {
                Empty
            }
            else -> {
                UserListContent(usersList)
            }
        }
    }

}
