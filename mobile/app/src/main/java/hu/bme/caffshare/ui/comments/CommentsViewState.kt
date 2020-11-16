package hu.bme.caffshare.ui.comments

import hu.bme.caffshare.ui.comments.model.CommentsData

sealed class CommentsViewState

object Loading : CommentsViewState()

object Error : CommentsViewState()

object Empty : CommentsViewState()

data class CommentsContent(
    val commentsData: CommentsData,
) : CommentsViewState()
