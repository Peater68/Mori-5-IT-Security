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
                imageUrl = "https://images-na.ssl-images-amazon.com/images/I/614FDmQ1p4L._AC_SL1001_.jpg",
            ),
            CaffFile(
                id = "0",
                author = "Borsy President Béla",
                imageUrl = "https://cf.bstatic.com/images/hotel/max500/211/211169617.jpg",
            ),
        )
    }

    suspend fun uploadFile(uri: Uri) = withIOContext {
        delay(1000)
        true
    }

}
