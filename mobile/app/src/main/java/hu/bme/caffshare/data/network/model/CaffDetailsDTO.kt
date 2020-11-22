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

/**
 *
 * @param id
 * @param creator
 * @param createdAt
 * @param width
 * @param height
 * @param duration
 * @param tags
 * @param caption
 * @param uploader
 */

data class CaffDetailsDTO(
    @Json(name = "id")
    val id: kotlin.String? = null,
    @Json(name = "creator")
    val creator: kotlin.String? = null,
    @Json(name = "createdAt")
    val createdAt: java.time.LocalDateTime? = null,
    @Json(name = "width")
    val width: kotlin.String? = null,
    @Json(name = "height")
    val height: kotlin.String? = null,
    @Json(name = "duration")
    val duration: kotlin.String? = null,
    @Json(name = "tags")
    val tags: kotlin.String? = null,
    @Json(name = "caption")
    val caption: kotlin.String? = null,
    @Json(name = "uploader")
    val uploader: kotlin.String? = null
)

