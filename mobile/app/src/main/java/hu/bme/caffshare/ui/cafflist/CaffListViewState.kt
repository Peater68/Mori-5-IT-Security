package hu.bme.caffshare.ui.cafflist

sealed class CaffListViewState

object Loading : CaffListViewState()

data class CaffListContent(val data: String = "") : CaffListViewState()
