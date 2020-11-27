package hu.bme.caffshare.domain

import android.net.Uri
import hu.bme.caffshare.data.network.NetworkDataSource
import hu.bme.caffshare.domain.model.DomainCaffDetails
import hu.bme.caffshare.domain.model.DomainCaffSum
import javax.inject.Inject

class CaffInteractor @Inject constructor(
    private val networkDataSource: NetworkDataSource
) {
    suspend fun deleteCaffById(caffId: String): Boolean = networkDataSource.deleteCaffById(caffId)

    // TODO: handle downloaded byte stream
    suspend fun downloadCaffFile(caffId: String): Boolean {
        val caffFileInputStream = networkDataSource.downloadCaffFile(caffId) ?: return false

        return false
    }

    suspend fun getCaffFiles(): List<DomainCaffSum>? = networkDataSource.getCaffFiles()

    suspend fun getCaffFileDetails(caffId: String): DomainCaffDetails? =
        networkDataSource.getCaffFileDetails(caffId)

    suspend fun uploadCaffFile(caffFileUri: Uri): Boolean =
        networkDataSource.uploadCaffFile(caffFileUri)
}