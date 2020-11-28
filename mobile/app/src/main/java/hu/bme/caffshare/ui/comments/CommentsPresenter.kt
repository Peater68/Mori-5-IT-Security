package hu.bme.caffshare.ui.comments

import co.zsmb.rainbowcake.withIOContext
import hu.bme.caffshare.domain.CommentInteractor
import hu.bme.caffshare.domain.UserInteractor
import hu.bme.caffshare.ui.comments.model.Comment
import javax.inject.Inject

class CommentsPresenter @Inject constructor(
    private val commentInteractor: CommentInteractor,
    private val userInteractor: UserInteractor
) {
    suspend fun getCommentsForCaffFile(caffFileId: String): List<Comment>? = withIOContext {
        val isUserAdmin = userInteractor.isUserAdmin()

        commentInteractor.getCommentsForCaff(caffFileId)?.map {
            Comment(
                id = it.id,
                text = it.comment,
                author = it.user.username,
                date = it.createdAt.toString(),
                isDeletable = isUserAdmin
            )
        }
    }

    suspend fun addComment(caffFileId: String, comment: String): Boolean = withIOContext {
        commentInteractor.addComment(caffFileId, comment)
    }

    suspend fun deleteComment(commentId: String): Boolean = withIOContext {
        commentInteractor.deleteComment(commentId)
    }
}