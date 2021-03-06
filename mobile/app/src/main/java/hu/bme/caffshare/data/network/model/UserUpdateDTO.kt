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
 * @param firstName
 * @param lastName
 * @param username
 * @param email
 */

@JsonClass(generateAdapter = true)
data class UserUpdateDTO(
    @Json(name = "firstName")
    val firstName: kotlin.String? = null,
    @Json(name = "lastName")
    val lastName: kotlin.String? = null,
    @Json(name = "username")
    val username: kotlin.String? = null,
    @Json(name = "email")
    val email: kotlin.String? = null
)

