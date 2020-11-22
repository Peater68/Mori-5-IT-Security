package hu.bme.caffshare.ui.boughtcaffdetails

import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import javax.inject.Inject

class BoughtCaffDetailsViewModel @Inject constructor(
    private val boughtCaffDetailsPresenter: BoughtCaffDetailsPresenter
) : RainbowCakeViewModel<BoughtCaffDetailsViewState>(Loading) {

    object DownloadSuccessful : OneShotEvent
    object DownloadFailed : OneShotEvent

    fun load(caffFileId: String) = execute {
        val details = boughtCaffDetailsPresenter.getCaffFileDetails(caffFileId)

        viewState = if (details == null) {
            Error
        } else {
            BoughtCaffDetailsContent(details)
        }
    }

    fun downloadCaffFile() = execute {
        (viewState as? BoughtCaffDetailsContent)?.let {
            val downloadResult = boughtCaffDetailsPresenter.downloadCaffFile(it.caffDetails.id)

            if (downloadResult) {
                postEvent(DownloadSuccessful)
            } else {
                postEvent(DownloadFailed)
            }
        }
    }
}
