package hu.bme.caffshare.ui.profile.boughtcafflist

import hu.bme.caffshare.ui.cafflist.model.CaffFile

sealed class BoughtCaffListViewState

object Loading : BoughtCaffListViewState()

object Error : BoughtCaffListViewState()

object Empty : BoughtCaffListViewState()

data class BoughtCaffListContent(val caffFiles: List<CaffFile> = emptyList()) :
    BoughtCaffListViewState()
