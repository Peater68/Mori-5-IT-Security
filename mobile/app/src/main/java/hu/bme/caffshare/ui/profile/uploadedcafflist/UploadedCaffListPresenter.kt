package hu.bme.caffshare.ui.profile.uploadedcafflist

import android.net.Uri
import co.zsmb.rainbowcake.withIOContext
import hu.bme.caffshare.domain.CaffInteractor
import hu.bme.caffshare.domain.model.DomainCaffSum
import hu.bme.caffshare.ui.cafflist.model.CaffFile
import hu.bme.caffshare.ui.cafflist.model.toUIModel
import javax.inject.Inject

class UploadedCaffListPresenter @Inject constructor(
    private val caffInteractor: CaffInteractor
) {

    suspend fun getCaffFiles(): List<CaffFile>? = withIOContext {
        caffInteractor.getUploadedCaffs()?.map(DomainCaffSum::toUIModel)
    }

    suspend fun uploadFile(uri: Uri) = withIOContext {
        caffInteractor.uploadCaffFile(uri)
    }

}
