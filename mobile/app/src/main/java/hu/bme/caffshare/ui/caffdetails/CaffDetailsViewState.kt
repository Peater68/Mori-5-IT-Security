package hu.bme.caffshare.ui.caffdetails

import hu.bme.caffshare.ui.caffdetails.model.CaffDetails

sealed class CaffDetailsViewState

object Loading : CaffDetailsViewState()

object Error : CaffDetailsViewState()

data class CaffDetailsContent(
    val caffDetails: CaffDetails,
    val isUserAdmin: Boolean
) : CaffDetailsViewState()
