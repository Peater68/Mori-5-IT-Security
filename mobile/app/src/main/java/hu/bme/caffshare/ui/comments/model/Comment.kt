package hu.bme.caffshare.ui.comments.model

data class Comment(
    val id: String,
    val text: String,
    val author: String,
    val date: String,
)