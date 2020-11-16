package hu.bme.caffshare.ui.comments

import co.zsmb.rainbowcake.withIOContext
import hu.bme.caffshare.ui.comments.model.Comment
import hu.bme.caffshare.ui.comments.model.UserData
import hu.bme.caffshare.ui.comments.model.UserRole
import javax.inject.Inject

class CommentsPresenter @Inject constructor() {
    suspend fun getCommentsForCaffFile(caffFileId: String): List<Comment>? = withIOContext {
        listOf(
            Comment(
                id = "id",
                text = "Fullos, patika",
                author = "Béla",
                date = "2020.02.02.",
            ),
            Comment(
                id = "id",
                text = "yeah",
                author = "Erik",
                date = "2020.02.02.",
            ),
            Comment(
                id = "id",
                text = "muhahahas dkjsadkj as dhhkjash sdkjsa hjkd hsakj d",
                author = "Erik",
                date = "2020.02.02.",
            ),
            Comment(
                id = "id",
                text = "Adom",
                author = "L",
                date = "2020.02.02.",
            ),
        )
    }

    suspend fun getCurrentUserData(): UserData? = withIOContext {
        UserData(
            id = "0",
            role = UserRole.USER
        )
    }
}