package hu.bme.caffshare.ui.blank

sealed class BlankViewState

object Loading : BlankViewState()

data class BlankReady(val data: String = "") : BlankViewState()
