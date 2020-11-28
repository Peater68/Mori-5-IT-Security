package hu.bme.caffshare.domain.model

import hu.bme.caffshare.data.network.model.TagDTO

data class DomainTag(
    val id: String,
    val title: String
)

fun TagDTO.toDomainModel(): DomainTag {
    return DomainTag(
        id = id,
        title = title
    )
}