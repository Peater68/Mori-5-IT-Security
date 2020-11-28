package hu.bme.caffshare.ui.admin.model

import hu.bme.caffshare.domain.model.DomainUser

data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val username: String,
    val isBanned: Boolean
)

fun DomainUser.toUIModel(): User {
    return User(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = "mockedemail@mockito.com",
        username = username,
        isBanned = false // TODO: is this even needed?
    )
}