package hu.bme.caffshare.ui.cafflist.model

import hu.bme.caffshare.domain.model.DomainCaffSum

data class CaffFile(
    val id: String,
    val author: String,
)

fun DomainCaffSum.toUIModel(): CaffFile {
    return CaffFile(
        id = id,
        author = creator ?: "Unknown"
    )
}