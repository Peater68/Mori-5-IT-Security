package hu.bme.caffshare.ui.caffdetails

import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import javax.inject.Inject

class CaffDetailsViewModel @Inject constructor(
    private val caffDetailsPresenter: CaffDetailsPresenter
) : RainbowCakeViewModel<CaffDetailsViewState>(Loading) {

    object PurchaseSuccessful : OneShotEvent
    object PurchaseFailed : OneShotEvent

    object DeleteSuccessful : OneShotEvent
    object DeleteFailed : OneShotEvent

    fun load(caffFileId: String) = execute {
        val details = caffDetailsPresenter.getCaffFileDetails(caffFileId)
        val isUserAdmin = caffDetailsPresenter.isUserAdmin()

        viewState = if (details == null) {
            Error
        } else {
            CaffDetailsContent(details, isUserAdmin)
        }
    }

    fun purchaseCaffFile() = execute {
        (viewState as? CaffDetailsContent)?.let {
            val purchaseResult = caffDetailsPresenter.purchaseCaffFile(it.caffDetails.id)

            if (purchaseResult) {
                postEvent(PurchaseSuccessful)
            } else {
                postEvent(PurchaseFailed)
            }
        }
    }

    fun deleteCaffFile() = execute {
        (viewState as? CaffDetailsContent)?.let {
            val deleteResult = caffDetailsPresenter.deleteCaffFile(it.caffDetails.id)

            if (deleteResult) {
                postEvent(DeleteSuccessful)
            } else {
                postEvent(DeleteFailed)
            }
        }
    }
}
