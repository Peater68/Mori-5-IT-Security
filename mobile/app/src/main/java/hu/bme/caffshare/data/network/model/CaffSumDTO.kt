/**
 * ITSECURITY API documentation
 * ITSECURITY API
 *
 * The version of the OpenAPI document: 1.0
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package hu.bme.caffshare.data.network.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 *
 * @param id ID of the document, what is represent the CAFF
 * @param createdAt
 * @param creator
 * @param uploader
 */

@JsonClass(generateAdapter = true)
data class CaffSumDTO(
    /* ID of the document, what is represent the CAFF */
    @Json(name = "id")
    val id: kotlin.String,
    @Json(name = "createdAt")
    val createdAt: String,
    @Json(name = "creator")
    val creator: kotlin.String? = null,
    @Json(name = "uploader")
    val uploader: kotlin.String? = null
)

