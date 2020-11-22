package hu.bme.caffshare.ui.boughtcaffdetails

import hu.bme.caffshare.ui.caffdetails.model.CaffDetails

sealed class BoughtCaffDetailsViewState

object Loading : BoughtCaffDetailsViewState()

object Error : BoughtCaffDetailsViewState()

data class BoughtCaffDetailsContent(
    val caffDetails: CaffDetails,
) : BoughtCaffDetailsViewState()
