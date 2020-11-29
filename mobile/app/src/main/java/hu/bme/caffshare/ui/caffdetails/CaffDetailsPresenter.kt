package hu.bme.caffshare.ui.caffdetails

import co.zsmb.rainbowcake.withIOContext
import hu.bme.caffshare.domain.CaffInteractor
import hu.bme.caffshare.domain.UserInteractor
import hu.bme.caffshare.ui.caffdetails.model.CaffDetails
import hu.bme.caffshare.ui.caffdetails.model.toUIModel
import javax.inject.Inject

class CaffDetailsPresenter @Inject constructor(
    private val caffInteractor: CaffInteractor,
    private val userInteractor: UserInteractor
) {
    suspend fun getCaffFileDetails(caffFileId: String): CaffDetails? = withIOContext {
        caffInteractor.getCaffFileDetails(caffFileId)?.toUIModel()
    }

    suspend fun purchaseCaffFile(caffId: String): Boolean = withIOContext {
        caffInteractor.purchaseCaffFile(caffId)
    }

    suspend fun deleteCaffFile(caffId: String): Boolean = withIOContext {
        caffInteractor.deleteCaffById(caffId)
    }

    suspend fun isUserAdmin(): Boolean = withIOContext {
        userInteractor.isUserAdmin()
    }
}