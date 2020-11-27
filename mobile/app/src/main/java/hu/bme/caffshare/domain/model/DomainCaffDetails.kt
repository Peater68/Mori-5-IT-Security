package hu.bme.caffshare.domain.model

import hu.bme.caffshare.data.network.model.CaffDetailsDTO
import hu.bme.caffshare.util.toLocalDateTime
import java.time.LocalDateTime

data class DomainCaffDetails(
    val id: String,
    val creator: String? = null,
    val createdAt: LocalDateTime,
    val width: String,
    val height: String,
    val duration: String,
    val tags: String? = null,
    val caption: String? = null,
    val uploader: String
)

fun CaffDetailsDTO.toDomainModel(): DomainCaffDetails {
    return DomainCaffDetails(
        id = id,
        creator = creator,
        createdAt = createdAt.toLocalDateTime(),
        width = width,
        height = height,
        duration = duration,
        tags = tags,
        caption = caption,
        uploader = uploader,
    )
}