package hu.bme.caffshare.ui.caffdetails.model

import java.time.LocalDateTime

data class CaffDetails(
    val id: String,
    val author: String,
    val imageUrl: String,
    val tags: List<String>,
    val caption: String,
    val date: LocalDateTime
)