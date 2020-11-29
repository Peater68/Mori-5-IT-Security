package hu.bme.caffshare.ui.boughtcaffdetails

import co.zsmb.rainbowcake.withIOContext
import hu.bme.caffshare.domain.CaffInteractor
import hu.bme.caffshare.ui.caffdetails.model.CaffDetails
import hu.bme.caffshare.ui.caffdetails.model.toUIModel
import javax.inject.Inject

class BoughtCaffDetailsPresenter @Inject constructor(
    private val caffInteractor: CaffInteractor
) {
    suspend fun getCaffFileDetails(caffFileId: String): CaffDetails? = withIOContext {
        caffInteractor.getCaffFileDetails(caffFileId)?.toUIModel()
    }

    suspend fun downloadCaffFile(id: String) = withIOContext {
        caffInteractor.downloadCaffFile(id)
    }
}