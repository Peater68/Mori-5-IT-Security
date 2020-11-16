package hu.bme.caffshare.ui.comments

import co.zsmb.rainbowcake.withIOContext
import hu.bme.caffshare.ui.comments.model.Comment
import hu.bme.caffshare.ui.comments.model.CommentsData
import hu.bme.caffshare.ui.comments.model.UserRole
import javax.inject.Inject

class CommentsPresenter @Inject constructor() {
    suspend fun getCommentsForCaffFile(caffFileId: String): CommentsData? = withIOContext {
        val comments = listOf(
            Comment(
                id = "id",
                text = "Fullos, patika",
                author = "Béla",
                date = "2020.02.02.",
                isDeletable = true
            ),
            Comment(
                id = "id",
                text = "yeah",
                author = "Erik",
                date = "2020.02.02.",
                isDeletable = false
            ),
            Comment(
                id = "id",
                text = "muhahahas dkjsadkj as dhhkjash sdkjsa hjkd hsakj d",
                author = "Erik",
                date = "2020.02.02.",
                isDeletable = false
            ),
            Comment(
                id = "id",
                text = "Adom",
                author = "L",
                date = "2020.02.02.",
                isDeletable = false
            ),
        )

        CommentsData(comments = comments, role = UserRole.USER)
    }
}