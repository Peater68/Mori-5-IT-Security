package hu.bme.caffshare.ui.comments

import co.zsmb.rainbowcake.withIOContext
import hu.bme.caffshare.ui.comments.model.Comment
import javax.inject.Inject

class CommentsPresenter @Inject constructor() {
    suspend fun getCommentsForCaffFile(caffFileId: String): List<Comment>? = withIOContext {
        listOf(
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
    }

    suspend fun addComment(comment: String) = withIOContext {
        // TODO
    }
}