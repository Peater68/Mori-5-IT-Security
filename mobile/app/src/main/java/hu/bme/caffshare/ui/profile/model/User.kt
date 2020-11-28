package hu.bme.caffshare.ui.profile.model

import hu.bme.caffshare.domain.model.DomainUser

data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String
)

fun DomainUser.toUIModel(): User {
    return User(
        id = id,
        firstName = firstName,
        lastName = lastName,
        username = username,
        email = email
    )
}