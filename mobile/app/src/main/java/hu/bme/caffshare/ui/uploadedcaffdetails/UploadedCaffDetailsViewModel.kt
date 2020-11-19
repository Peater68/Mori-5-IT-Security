package hu.bme.caffshare.ui.uploadedcaffdetails

import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import javax.inject.Inject

class UploadedCaffDetailsViewModel @Inject constructor(
    private val uploadedCaffDetailsPresenter: UploadedCaffDetailsPresenter
) : RainbowCakeViewModel<UploadedCaffDetailsViewState>(Loading) {

    object DownloadSuccessful : OneShotEvent
    object DownloadFailed : OneShotEvent

    object DeleteSuccessful : OneShotEvent
    object DeleteFailed : OneShotEvent

    fun load(caffFileId: String) = execute {
        val details = uploadedCaffDetailsPresenter.getCaffFileDetails(caffFileId)

        viewState = if (details == null) {
            Error
        } else {
            UploadedCaffDetailsContent(details)
        }
    }

    fun downloadCaffFile() = execute {
        (viewState as? UploadedCaffDetailsContent)?.let {
            val downloadResult = uploadedCaffDetailsPresenter.downloadCaffFile(it.caffDetails.id)

            if (downloadResult) {
                postEvent(DownloadSuccessful)
            } else {
                postEvent(DownloadFailed)
            }
        }
    }

    fun deleteCaffFile() = execute {
        (viewState as? UploadedCaffDetailsContent)?.let {
            val deleteResult = uploadedCaffDetailsPresenter.deleteCaffFile(it.caffDetails.id)

            if (deleteResult) {
                postEvent(DeleteSuccessful)
            } else {
                postEvent(DeleteFailed)
            }
        }
    }
}
