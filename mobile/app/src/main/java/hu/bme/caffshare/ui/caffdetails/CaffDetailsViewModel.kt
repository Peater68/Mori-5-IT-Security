package hu.bme.caffshare.ui.caffdetails

import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import javax.inject.Inject

class CaffDetailsViewModel @Inject constructor(
    private val caffDetailsPresenter: CaffDetailsPresenter
) : RainbowCakeViewModel<CaffDetailsViewState>(Loading) {

    object PurchaseSuccessful : OneShotEvent
    object PurchaseFailed : OneShotEvent

    fun load(caffFileId: String) = execute {
        val details = caffDetailsPresenter.getCaffFileDetails(caffFileId)

        viewState = if (details == null) {
            Error
        } else {
            CaffDetailsContent(details)
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
}
