package hu.bme.caffshare.ui.comments

import hu.bme.caffshare.ui.comments.model.Comment
import hu.bme.caffshare.ui.comments.model.UserData

sealed class CommentsViewState

object Loading : CommentsViewState()

object Error : CommentsViewState()

object Empty : CommentsViewState()

data class CommentsContent(
    val userData: UserData,
    val comments: List<Comment>,
) : CommentsViewState()
