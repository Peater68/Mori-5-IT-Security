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
                imageUrl = "https://i.pinimg.com/564x/3f/a1/f4/3fa1f40faa21f99cde076b9bd600f929.jpg",
            ),
            CaffFile(
                id = "0",
                author = "Borsy President Béla",
                imageUrl = "https://images-na.ssl-images-amazon.com/images/I/614FDmQ1p4L._AC_SL1001_.jpg",
            ),
            CaffFile(
                id = "0",
                author = "Kapitány Interceptor Erik",
                imageUrl = "https://assets.mmsrg.com/isr/166325/c1/-/pixelboxx-mss-80570025/mobile_786_587_png/TRUST-GXT-103-Gav-vezet%C3%A9k-n%C3%A9lk%C3%BCli-optikai-gaming-eg%C3%A9r-%2823213%29",
            ),
            CaffFile(
                id = "0",
                author = "Laki Sketch Dávid",
                imageUrl = "https://scontent-vie1-1.xx.fbcdn.net/v/t1.15752-9/125260092_3426639040783418_1284246618156808486_n.jpg?_nc_cat=111&ccb=2&_nc_sid=ae9488&_nc_ohc=TVRrnI4d4q4AX-v1GIN&_nc_ht=scontent-vie1-1.xx&oh=3fe501bea93ca1d9ce6303b5cc06574d&oe=5FD47445",
            ),
            CaffFile(
                id = "0",
                author = "Deák Wiki Dávid",
                imageUrl = "https://www.azauto.hu/media/2020/04/01-532x315.jpg",
            ),
            CaffFile(
                id = "0",
                author = "Borsy President Béla",
                imageUrl = "https://cf.bstatic.com/images/hotel/max500/211/211169617.jpg",
            ),
        )
    }

}
