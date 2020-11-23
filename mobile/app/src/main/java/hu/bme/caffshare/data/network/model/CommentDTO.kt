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
 * @param id
 * @param comment
 * @param user
 * @param createdAt
 */

@JsonClass(generateAdapter = true)
data class CommentDTO(
    @Json(name = "id")
    val id: kotlin.String? = null,
    @Json(name = "comment")
    val comment: kotlin.String? = null,
    @Json(name = "user")
    val user: ReducedUserDTO? = null,
    @Json(name = "createdAt")
    val createdAt: java.time.LocalDateTime? = null
)

