package hu.bme.caffshare.ui.admin

import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class UserListViewModel @Inject constructor(
    private val userListPresenter: UserListPresenter
) : RainbowCakeViewModel<UserListViewState>(Loading) {

    object BanError : OneShotEvent
    object MakeUserAdminError : OneShotEvent

    fun isUserAdmin() = runBlocking {
        userListPresenter.isUserAdmin()
    }

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

    fun banUser(userId: String) = execute {
        val isDeleteSuccessful = userListPresenter.banUser(userId)

        if (isDeleteSuccessful) {
            viewState = Loading
            load()
        } else {
            postEvent(BanError)
        }
    }

    fun makeUserAdmin(userId: String) = execute {
        val isAdminMakingSuccessful = userListPresenter.makeUserAdmin(userId)

        if (isAdminMakingSuccessful) {
            viewState = Loading
            load()
        } else {
            postEvent(MakeUserAdminError)
        }
    }
}
