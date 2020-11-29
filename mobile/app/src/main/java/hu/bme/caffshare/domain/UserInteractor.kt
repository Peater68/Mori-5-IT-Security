package hu.bme.caffshare.domain

import hu.bme.caffshare.data.local.TokenDataSource
import hu.bme.caffshare.data.network.NetworkDataSource
import hu.bme.caffshare.domain.model.DomainRole
import javax.inject.Inject

class UserInteractor @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val tokenDataSource: TokenDataSource
) {

    suspend fun getCurrentUserProfile() = networkDataSource.getCurrentUserProfile()

    suspend fun banUser(userId: String) = networkDataSource.banUser(userId)

    suspend fun getAllUsers() = networkDataSource.getAllUsers()

    suspend fun makeUserAdmin(userId: String) = networkDataSource.makeUserAdmin(userId)

    suspend fun updateCurrentUser(
        firstName: String,
        lastName: String,
        username: String,
        email: String,
    ) = networkDataSource.updateCurrentUser(
        firstName = firstName,
        lastName = lastName,
        username = username,
        email = email,
    )

    suspend fun getCurrentUserRole() = getCurrentUserProfile()?.role

    suspend fun isUserAdmin() = getCurrentUserRole() == DomainRole.ADMIN
}