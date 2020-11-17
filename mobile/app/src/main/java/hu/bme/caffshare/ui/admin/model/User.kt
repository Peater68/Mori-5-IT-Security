package hu.bme.caffshare.ui.admin.model

data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val username: String,
    val isBanned: Boolean
)