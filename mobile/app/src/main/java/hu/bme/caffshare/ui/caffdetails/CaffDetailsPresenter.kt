package hu.bme.caffshare.ui.caffdetails

import co.zsmb.rainbowcake.withIOContext
import hu.bme.caffshare.ui.caffdetails.model.CaffDetails
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import javax.inject.Inject

class CaffDetailsPresenter @Inject constructor() {
    suspend fun getCaffFileDetails(caffFileId: String): CaffDetails? = withIOContext {
        CaffDetails(
            id = caffFileId,
            author = "Borsy President Béla",
            tags = listOf("my house", "bb"),
            caption = "Pres Béla is the new man in town",
            date = LocalDateTime.now().toString(),
        )
    }

    suspend fun purchaseCaffFile(id: String) = withIOContext {
        delay(1000)
        true
    }

    suspend fun deleteCaffFile(id: String): Boolean = withIOContext {
        delay(1000)
        true
    }

    suspend fun isUserAdmin(): Boolean = withIOContext {
        delay(1000)
        false
    }
}