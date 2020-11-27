package hu.bme.caffshare.ui.cafflist

import co.zsmb.rainbowcake.withIOContext
import hu.bme.caffshare.ui.cafflist.model.CaffFile
import javax.inject.Inject

class CaffListPresenter @Inject constructor() {

    suspend fun getCaffFiles(): List<CaffFile> = withIOContext {
        listOf(
            CaffFile(
                id = "0",
                author = "Kapitány Interceptor Erik",
            ),
            CaffFile(
                id = "0",
                author = "Borsy President Béla",
            ),
            CaffFile(
                id = "0",
                author = "Kapitány Interceptor Erik",
            ),
            CaffFile(
                id = "0",
                author = "Laki Sketch Dávid",
            ),
            CaffFile(
                id = "0",
                author = "Deák Wiki Dávid",
            ),
            CaffFile(
                id = "0",
                author = "Borsy President Béla",
            ),
        )
    }

}
