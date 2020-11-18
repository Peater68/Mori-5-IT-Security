package hu.bme.caffshare.ui.profile.uploadedcafflist

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import javax.inject.Inject

class UploadedCaffListViewModel @Inject constructor(
    private val uploadedCaffListPresenter: UploadedCaffListPresenter
) : RainbowCakeViewModel<UploadedCaffListViewState>(Loading) {

    fun load() = execute {
        val caffList = uploadedCaffListPresenter.getCaffFiles()

        viewState = UploadedCaffListContent(caffList)
    }

}
