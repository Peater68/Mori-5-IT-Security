package hu.bme.caffshare.ui.cafflist

import co.zsmb.rainbowcake.withIOContext
import hu.bme.caffshare.domain.CaffInteractor
import hu.bme.caffshare.domain.model.DomainCaffSum
import hu.bme.caffshare.ui.cafflist.model.CaffFile
import hu.bme.caffshare.ui.cafflist.model.toUIModel
import javax.inject.Inject

class CaffListPresenter @Inject constructor(
    private val caffInteractor: CaffInteractor
) {

    suspend fun getCaffFiles(): List<CaffFile>? = withIOContext {
        caffInteractor.getCaffFiles()?.map(DomainCaffSum::toUIModel)
    }

}
