package hu.bme.caffshare.domain

import hu.bme.caffshare.data.local.TokenDataSource
import hu.bme.caffshare.data.network.NetworkDataSource
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val tokenDataSource: TokenDataSource
) {
    suspend fun login(username: String, password: String) =
        networkDataSource.login(username, password)

    suspend fun register(
        username: String,
        password: String,
        email: String,
        firstName: String,
        lastName: String
    ) = networkDataSource.register(
        username = username,
        password = password,
        email = email,
        firstName = firstName,
        lastName = lastName,
    )

    suspend fun changePassword(currentPassword: String, newPassword: String) =
        networkDataSource.changePassword(currentPassword, newPassword)

    fun logout() {
        tokenDataSource.removeTokens()
    }
}