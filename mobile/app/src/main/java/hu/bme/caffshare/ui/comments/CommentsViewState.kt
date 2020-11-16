package hu.bme.caffshare.ui.comments

import hu.bme.caffshare.ui.comments.model.Comment

sealed class CommentsViewState

object Loading : CommentsViewState()

object Error : CommentsViewState()

object Empty : CommentsViewState()

data class CommentsContent(
    val comments: List<Comment>,
) : CommentsViewState()
