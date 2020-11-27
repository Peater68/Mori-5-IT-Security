package hu.bme.caffshare.domain

import hu.bme.caffshare.data.network.NetworkDataSource
import hu.bme.caffshare.domain.model.DomainUser
import javax.inject.Inject

class UserInteractor @Inject constructor(
    private val networkDataSource: NetworkDataSource
) {

    suspend fun getCurrentUserProfile(): DomainUser? = networkDataSource.getCurrentUserProfile()

    suspend fun banUser(userId: String): Boolean = networkDataSource.banUser(userId)

    suspend fun deleteCurrentUser(): Boolean = networkDataSource.deleteCurrentUser()

    suspend fun getAllUsers(): List<DomainUser>? = networkDataSource.getAllUsers()

    suspend fun makeUserAdmin(userId: String): Boolean = networkDataSource.makeUserAdmin(userId)

    suspend fun updateCurrentUser(
        firstName: String,
        lastName: String,
        username: String,
        email: String,
    ): Boolean = networkDataSource.updateCurrentUser(
        firstName = firstName,
        lastName = lastName,
        username = username,
        email = email,
    )
}