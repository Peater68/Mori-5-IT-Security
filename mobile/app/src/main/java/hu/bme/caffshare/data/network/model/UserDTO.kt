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
 * @param firstName
 * @param lastName
 * @param username
 * @param role
 * @param createdAt
 * @param updatedAt
 */

@JsonClass(generateAdapter = true)
data class UserDTO(
    @Json(name = "id")
    val id: kotlin.String? = null,
    @Json(name = "firstName")
    val firstName: kotlin.String? = null,
    @Json(name = "lastName")
    val lastName: kotlin.String? = null,
    @Json(name = "username")
    val username: kotlin.String? = null,
    @Json(name = "role")
    val role: UserDTO.Role? = null,
    @Json(name = "createdAt")
    val createdAt: String? = null,
    @Json(name = "updatedAt")
    val updatedAt: String? = null
) {

    /**
     *
     * Values: ADMIN,WORKER
     */

    enum class Role(val value: kotlin.String) {
        @Json(name = "ADMIN")
        ADMIN("ADMIN"),
        @Json(name = "WORKER")
        WORKER("WORKER");
    }
}

