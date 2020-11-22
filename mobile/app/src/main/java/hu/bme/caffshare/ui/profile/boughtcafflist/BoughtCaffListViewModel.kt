package hu.bme.caffshare.ui.profile.boughtcafflist

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import javax.inject.Inject

class BoughtCaffListViewModel @Inject constructor(
    private val boughtCaffListPresenter: BoughtCaffListPresenter
) : RainbowCakeViewModel<BoughtCaffListViewState>(Loading) {

    fun load() = execute {
        val caffList = boughtCaffListPresenter.getCaffFiles()

        viewState = BoughtCaffListContent(caffList)
    }

}
