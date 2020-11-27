package hu.bme.caffshare.domain.model

import hu.bme.caffshare.data.network.model.CommentDTO
import hu.bme.caffshare.util.toLocalDateTime
import java.time.LocalDateTime

class DomainComment(
    val id: String,
    val comment: String,
    val user: DomainReducedUser,
    val createdAt: LocalDateTime
)

fun CommentDTO.toDomainModel(): DomainComment {
    return DomainComment(
        id = id,
        comment = comment,
        user = user.toDomainModel(),
        createdAt = createdAt.toLocalDateTime()
    )
}