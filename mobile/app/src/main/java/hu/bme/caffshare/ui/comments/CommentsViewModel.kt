package hu.bme.caffshare.ui.comments

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import javax.inject.Inject

class CommentsViewModel @Inject constructor(
    private val commentsPresenter: CommentsPresenter
) : RainbowCakeViewModel<CommentsViewState>(Loading) {

    fun load(caffFileId: String) = execute {
        val commentsData = commentsPresenter.getCommentsForCaffFile(caffFileId)

        viewState = if (commentsData == null) {
            Error
        } else {
            if (commentsData.comments.isEmpty()) {
                Empty
            } else {
                CommentsContent(commentsData)
            }
        }
    }
}
