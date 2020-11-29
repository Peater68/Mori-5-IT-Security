package hu.bme.caffshare.domain

import android.content.Context
import android.net.Uri
import hu.bme.caffshare.data.network.NetworkDataSource
import hu.bme.caffshare.util.copyInputStreamToFile
import java.io.File
import javax.inject.Inject

class CaffInteractor @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val context: Context
) {
    suspend fun deleteCaffById(caffId: String) = networkDataSource.deleteCaffById(caffId)

    // TODO: handle downloaded byte stream
    suspend fun downloadCaffFile(caffId: String): Boolean {
        val caffFileInputStream = networkDataSource.downloadCaffFile(caffId) ?: return false

        File(context.cacheDir, "file.caff").copyInputStreamToFile(caffFileInputStream)

        return true
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