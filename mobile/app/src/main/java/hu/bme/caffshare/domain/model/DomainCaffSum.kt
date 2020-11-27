package hu.bme.caffshare.domain.model

import hu.bme.caffshare.data.network.model.CaffSumDTO
import hu.bme.caffshare.util.toLocalDateTime
import java.time.LocalDateTime

data class DomainCaffSum(
    val id: String,
    val createdAt: LocalDateTime,
    val creator: String? = null,
    val uploader: String
)

fun CaffSumDTO.toDomainModel(): DomainCaffSum {
    return DomainCaffSum(
        id = id,
        createdAt = createdAt.toLocalDateTime(),
        creator = creator,
        uploader = uploader,
    )
}