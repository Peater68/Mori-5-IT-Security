package hu.bme.caffshare.ui.comments

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import javax.inject.Inject

class CommentsViewModel @Inject constructor(
    private val commentsPresenter: CommentsPresenter
) : RainbowCakeViewModel<CommentsViewState>(Loading) {

    fun load(caffFileId: String) = execute {
        val comments = commentsPresenter.getCommentsForCaffFile(caffFileId)
        val userData = commentsPresenter.getCurrentUserData()

        viewState = if (comments == null || userData == null) {
            Error
        } else {
            if (comments.isEmpty()) {
                Empty
            } else {
                CommentsContent(comments = comments, userData = userData)
            }
        }
    }
}
