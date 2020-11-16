package hu.bme.caffshare.ui.caffdetails.model

data class CaffDetails(
    val id: String,
    val author: String,
    val imageUrl: String,
    val tags: List<String>,
    val caption: String,
    val date: String
)