package hu.bme.caffshare.ui.admin

import co.zsmb.rainbowcake.withIOContext
import hu.bme.caffshare.domain.UserInteractor
import hu.bme.caffshare.domain.model.DomainUser
import hu.bme.caffshare.ui.admin.model.User
import hu.bme.caffshare.ui.admin.model.toUIModel
import javax.inject.Inject

class UserListPresenter @Inject constructor(
    private val userInteractor: UserInteractor
) {

    //TODO
    suspend fun isUserAdmin(): Boolean = withIOContext {
        true
    }

    suspend fun getUsers(): List<User>? = withIOContext {
        userInteractor.getAllUsers()?.map(DomainUser::toUIModel)
    }

    suspend fun banUser(userId: String): Boolean = withIOContext {
        userInteractor.banUser(userId)
    }

    suspend fun makeUserAdmin(userId: String) = withIOContext {
        userInteractor.makeUserAdmin(userId)
    }
}
