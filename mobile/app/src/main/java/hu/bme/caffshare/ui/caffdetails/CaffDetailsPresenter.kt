package hu.bme.caffshare.ui.caffdetails

import co.zsmb.rainbowcake.withIOContext
import hu.bme.caffshare.domain.CaffInteractor
import hu.bme.caffshare.ui.caffdetails.model.CaffDetails
import hu.bme.caffshare.ui.caffdetails.model.toUIModel
import kotlinx.coroutines.delay
import javax.inject.Inject

class CaffDetailsPresenter @Inject constructor(
    private val caffInteractor: CaffInteractor
) {
    suspend fun getCaffFileDetails(caffFileId: String): CaffDetails? = withIOContext {
        caffInteractor.getCaffFileDetails(caffFileId)?.toUIModel()
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