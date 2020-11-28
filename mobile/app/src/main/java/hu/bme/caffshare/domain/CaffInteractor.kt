package hu.bme.caffshare.domain

import android.net.Uri
import hu.bme.caffshare.data.network.NetworkDataSource
import javax.inject.Inject

class CaffInteractor @Inject constructor(
    private val networkDataSource: NetworkDataSource
) {
    suspend fun deleteCaffById(caffId: String) = networkDataSource.deleteCaffById(caffId)

    // TODO: handle downloaded byte stream
    suspend fun downloadCaffFile(caffId: String): Boolean {
        val caffFileInputStream = networkDataSource.downloadCaffFile(caffId) ?: return false

        return false
    }

    suspend fun getCaffFiles() = networkDataSource.getCaffFiles()

    suspend fun getCaffFileDetails(caffId: String) =
        networkDataSource.getCaffFileDetails(caffId)

    suspend fun uploadCaffFile(caffFileUri: Uri) =
        networkDataSource.uploadCaffFile(caffFileUri)

    suspend fun purchaseCaffFile(caffId: String) = networkDataSource.buyCaff(caffId)

    suspend fun getBoughtCaffs() = networkDataSource.getBoughtCaffs()

    suspend fun getUploadedCaffs() = networkDataSource.getUploadedCaffs()

    suspend fun getTags() = networkDataSource.getTags()
}