package hu.bme.caffshare.domain.model

import hu.bme.caffshare.data.network.model.ReducedUserDTO

data class DomainReducedUser(
    val username: String,
    val firstName: String,
    val lastName: String
)

fun ReducedUserDTO.toDomainModel(): DomainReducedUser {
    return DomainReducedUser(
        username = username,
        firstName = firstName,
        lastName = lastName,
    )
}