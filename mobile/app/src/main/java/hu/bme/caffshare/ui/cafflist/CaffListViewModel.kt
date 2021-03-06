package hu.bme.caffshare.ui.cafflist

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import javax.inject.Inject

class CaffListViewModel @Inject constructor(
    private val caffListPresenter: CaffListPresenter
) : RainbowCakeViewModel<CaffListViewState>(Loading) {

    fun load() = execute {
        val caffList = caffListPresenter.getCaffFiles()

        viewState = when {
            caffList == null -> Error
            caffList.isEmpty() -> Empty
            else -> CaffListContent(caffList)
        }
    }

}
