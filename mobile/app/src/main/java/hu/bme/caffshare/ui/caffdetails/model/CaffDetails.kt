package hu.bme.caffshare.ui.caffdetails.model

import hu.bme.caffshare.domain.model.DomainCaffDetails

data class CaffDetails(
    val id: String,
    val author: String,
    val tags: List<String>,
    val caption: String,
    val date: String
)

fun DomainCaffDetails.toUIModel(): CaffDetails {
    return CaffDetails(
        id = id,
        author = creator ?: "Unknown creator",
        tags = tags.map { it.title },
        caption = caption ?: "No caption",
        date = createdAt.toString()
    )
}