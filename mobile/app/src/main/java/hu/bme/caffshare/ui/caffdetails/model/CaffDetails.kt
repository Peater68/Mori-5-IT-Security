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
        author = uploader,
        tags = listOf(tags ?: "kldnfjasdn"),
        caption = caption ?: "No caption",
        date = createdAt.toString()
    )
}