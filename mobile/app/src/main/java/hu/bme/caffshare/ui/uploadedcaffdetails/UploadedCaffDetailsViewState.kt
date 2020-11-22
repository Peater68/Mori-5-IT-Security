package hu.bme.caffshare.ui.uploadedcaffdetails

import hu.bme.caffshare.ui.caffdetails.model.CaffDetails

sealed class UploadedCaffDetailsViewState

object Loading : UploadedCaffDetailsViewState()

object Error : UploadedCaffDetailsViewState()

data class UploadedCaffDetailsContent(
    val caffDetails: CaffDetails,
) : UploadedCaffDetailsViewState()
