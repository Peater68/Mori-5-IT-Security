package hu.bme.caffshare.domain

import hu.bme.caffshare.data.network.NetworkDataSource
import hu.bme.caffshare.domain.model.DomainUser
import javax.inject.Inject

class UserInteractor @Inject constructor(
    private val networkDataSource: NetworkDataSource
) {
    suspend fun getCurrentUserProfile(): DomainUser? {
        return networkDataSource.getCurrentUserProfile()
    }
}