package hu.bme.caffshare.ui.cafflist

import hu.bme.caffshare.ui.cafflist.model.CaffFile

sealed class CaffListViewState

object Loading : CaffListViewState()

data class CaffListContent(val caffFiles: List<CaffFile> = emptyList()) : CaffListViewState()
