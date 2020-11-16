package hu.bme.caffshare.ui.comments

import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import javax.inject.Inject

class CommentsViewModel @Inject constructor(
    private val commentsPresenter: CommentsPresenter
) : RainbowCakeViewModel<CommentsViewState>(Loading) {

    object CommentSentSuccessfully : OneShotEvent
    object CommentSendingError : OneShotEvent

    fun load(caffFileId: String) = execute {
        val comments = commentsPresenter.getCommentsForCaffFile(caffFileId)

        viewState = if (comments == null) {
            Error
        } else {
            if (comments.isEmpty()) {
                Empty
            } else {
                CommentsContent(comments)
            }
        }
    }

    fun addComment(comment: String) = execute {
        val isSuccessful = commentsPresenter.addComment(comment)

        if (isSuccessful) {
            postEvent(CommentSentSuccessfully)
        } else {
            postEvent(CommentSendingError)
        }
    }
}
