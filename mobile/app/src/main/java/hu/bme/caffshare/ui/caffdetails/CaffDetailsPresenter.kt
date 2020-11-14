package hu.bme.caffshare.ui.caffdetails

import co.zsmb.rainbowcake.withIOContext
import hu.bme.caffshare.ui.caffdetails.model.CaffDetails
import java.time.LocalDateTime
import javax.inject.Inject

class CaffDetailsPresenter @Inject constructor() {
    suspend fun getCaffFileDetails(caffFileId: String): CaffDetails? = withIOContext {
        CaffDetails(
            id = caffFileId,
            author = "Borsy President Béla",
            imageUrl = "https://cf.bstatic.com/images/hotel/max500/211/211169617.jpg",
            tags = listOf("my house", "bb"),
            caption = "Pres Béla is the new man in town",
            date = LocalDateTime.now(),
        )
    }
}