package hu.bme.caffshare.data.network.api


import hu.bme.caffshare.data.network.model.UserDTO
import hu.bme.caffshare.data.network.model.UserRegistrationDTO
import hu.bme.caffshare.data.network.model.UserUpdateDTO
import retrofit2.Response
import retrofit2.http.*

interface UserApi {
    /**
     *
     * Ban user by id
     * Responses:
     *  - 204: No Content
     *  - 401: Unauthorized
     *  - 403: Forbidden
     *  - 404: Not Found
     *  - 500: Internal Server Error
     *
     * @param userId
     * @return [Unit]
     */
    @PUT("api/users/{userId}/ban")
    suspend fun banUserById(@Path("userId") userId: kotlin.String): Response<Unit>

    /**
     *
     * Create new user
     * Responses:
     *  - 201: Created
     *  - 400: Bad Request
     *  - 401: Unauthorized
     *  - 403: Forbidden
     *  - 500: Not Found
     *
     * @param userRegistrationDTO  (optional)
     * @return [UserDTO]
     */
    @POST("api/users")
    suspend fun createUser(@Body userRegistrationDTO: UserRegistrationDTO? = null): Response<UserDTO>

    /**
     *
     * Delete current user profile
     * Responses:
     *  - 204: Non-Authoritative Information
     *  - 401: Unauthorized
     *  - 403: Forbidden
     *  - 500: Internal Server Error
     *
     * @return [Unit]
     */
    @DELETE("api/users/me")
    suspend fun deleteMe(): Response<Unit>

    /**
     * Your GET endpoint
     * Get current user profile
     * Responses:
     *  - 200: OK
     *  - 401: Unauthorized
     *  - 403: Forbidden
     *  - 500: Internal Server Error
     *
     * @return [UserDTO]
     */
    @GET("api/users/me")
    suspend fun getMe(): Response<UserDTO>

    /**
     * Your GET endpoint
     * Get all users
     * Responses:
     *  - 200: OK
     *  - 401: Unauthorized
     *  - 403: Forbidden
     *  - 500: Internal Server Error
     *
     * @return [kotlin.collections.List<UserDTO>]
     */
    @GET("api/users")
    suspend fun getUsers(): Response<kotlin.collections.List<UserDTO>>

    /**
     *
     * Make user admin by id
     * Responses:
     *  - 204: No Content
     *  - 401: Unauthorized
     *  - 403: Forbidden
     *  - 404: Not Found
     *  - 500: Internal Server Error
     *
     * @param userId
     * @return [Unit]
     */
    @PUT("api/user/{userId}/makeadmin")
    suspend fun makeUserAdmin(@Path("userId") userId: kotlin.String): Response<Unit>

    /**
     *
     * Update current user profile
     * Responses:
     *  - 200: OK
     *  - 400: Bad Request
     *  - 401: Unauthorized
     *  - 403: Forbidden
     *  - 500: Internal Server Error
     *
     * @param userUpdateDTO  (optional)
     * @return [UserDTO]
     */
    @PUT("api/users/me")
    suspend fun updateMe(@Body userUpdateDTO: UserUpdateDTO? = null): Response<UserDTO>

}
