package hu.bme.caffshare.domain

import hu.bme.caffshare.data.network.NetworkDataSource
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val networkDataSource: NetworkDataSource
) {
    suspend fun login(username: String, password: String): Boolean {
        return networkDataSource.login(username, password)
    }

    suspend fun register(
        username: String,
        password: String,
        email: String,
        firstName: String,
        lastName: String
    ): Boolean {
        return networkDataSource.register(
            username = username,
            password = password,
            email = email,
            firstName = firstName,
            lastName = lastName,
        )
    }

    suspend fun changePassword(currentPassword: String, newPassword: String): Boolean {
        return networkDataSource.changePassword(currentPassword, newPassword)
    }
}