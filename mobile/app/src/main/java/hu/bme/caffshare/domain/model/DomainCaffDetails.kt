package hu.bme.caffshare.domain.model

import hu.bme.caffshare.data.network.model.CaffDetailsDTO
import hu.bme.caffshare.data.network.model.TagDTO
import hu.bme.caffshare.util.toLocalDateTime
import java.time.LocalDateTime

data class DomainCaffDetails(
    val id: String,
    val creator: String? = null,
    val createdAt: LocalDateTime,
    val tags: List<DomainTag>,
    val duration: String? = null,
    val caption: String? = null,
)

fun CaffDetailsDTO.toDomainModel(): DomainCaffDetails {
    return DomainCaffDetails(
        id = id,
        creator = creator,
        createdAt = createdAt.toLocalDateTime(),
        duration = duration,
        tags = tags.map(TagDTO::toDomainModel),
        caption = caption,
    )
}