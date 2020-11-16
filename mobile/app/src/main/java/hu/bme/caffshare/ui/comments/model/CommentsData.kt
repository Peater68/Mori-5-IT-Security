package hu.bme.caffshare.ui.comments.model

data class CommentsData(
    val role: UserRole,
    val comments: List<Comment>
)