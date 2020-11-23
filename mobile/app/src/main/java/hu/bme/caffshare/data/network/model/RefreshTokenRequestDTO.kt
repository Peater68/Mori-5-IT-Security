package hu.bme.caffshare.data.network.model

import com.squareup.moshi.Json

data class RefreshTokenRequestDTO(
    @Json(name = "refreshToken")
    val refreshToken: String
)