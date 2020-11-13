package hu.bme.caffshare.ui.cafflist

import co.zsmb.rainbowcake.withIOContext
import javax.inject.Inject

class CaffListPresenter @Inject constructor() {

    suspend fun getData(): String = withIOContext {
        ""
    }

}
