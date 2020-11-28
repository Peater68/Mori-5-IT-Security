package hu.bme.caffshare.domain.model

import hu.bme.caffshare.data.network.model.UserDTO
import hu.bme.caffshare.util.toLocalDateTime
import java.time.LocalDateTime

data class DomainUser(
    val id: String,
    val firstName: String,
    val lastName: String,
    val username: String,
    val role: DomainRole,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val email: String,
    val isBanned: Boolean
)

fun UserDTO.toDomainModel(): DomainUser {
    return DomainUser(
        id = id,
        firstName = firstName,
        lastName = lastName,
        username = username,
        role = role.toDomainModel(),
        createdAt = createdAt.toLocalDateTime(),
        updatedAt = updatedAt.toLocalDateTime(),
        email = email ?: "",
        isBanned = banned ?: false
    )
}
