package hu.bme.caffshare.ui.profile.uploadedcafflist

import android.net.Uri
import co.zsmb.rainbowcake.withIOContext
import hu.bme.caffshare.ui.cafflist.model.CaffFile
import kotlinx.coroutines.delay
import javax.inject.Inject

class UploadedCaffListPresenter @Inject constructor() {

    suspend fun getCaffFiles(): List<CaffFile> = withIOContext {
        listOf(
            CaffFile(
                id = "0",
                author = "Borsy President Béla",
            ),
            CaffFile(
                id = "0",
                author = "Borsy President Béla",
            ),
        )
    }

    suspend fun uploadFile(uri: Uri) = withIOContext {
        delay(1000)
        true
    }

}
