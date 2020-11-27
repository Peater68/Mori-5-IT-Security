package hu.bme.caffshare.ui.admin

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.caffshare.ui.admin.model.User
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class UserListViewModel @Inject constructor(
    private val userListPresenter: UserListPresenter
) : RainbowCakeViewModel<UserListViewState>(Loading) {

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

    fun deleteUser(user: User) = execute {
        val usersList = userListPresenter.deleteUser(user)

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

    fun makeUserAdmin(user: User) = execute {
        userListPresenter.makeUserAdmin(user)

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

    fun banUser(user: User) = execute {
        userListPresenter.banUser(user)

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
