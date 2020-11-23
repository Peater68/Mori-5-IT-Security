package hu.bme.caffshare.domain

import hu.bme.caffshare.data.network.NetworkDataSource
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val networkDataSource: NetworkDataSource
) {
    suspend fun login(username: String, password: String): Boolean {
        return networkDataSource.login(username, password)
    }
}