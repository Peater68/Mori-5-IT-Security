package hu.bme.caffshare.data.network.api

import hu.bme.caffshare.data.network.model.*
import retrofit2.Response
import retrofit2.http.*

interface AuthApi {
    /**
     *
     * Change own password by user
     * Responses:
     *  - 200: OK
     *  - 400: Bad Request
     *  - 401: Unauthorized
     *  - 403: Forbidden
     *  - 404: Not Found
     *  - 500: Internal Server Error
     *
     * @param passwordChangeDTO  (optional)
     * @return [Unit]
     */
    @POST("api/auth/me/changepassword")
    suspend fun changePassword(@Body passwordChangeDTO: PasswordChangeDTO? = null): Response<Unit>

    /**
     *
     * Get access token with refresh token
     * Responses:
     *  - 200: OK
     *  - 400: Not Implemented
     *  - 500: Internal Server Error
     *
     * @param refreshTokenDTO  (optional)
     * @return [TokensDTO]
     */
    @POST("api/auth/token")
    suspend fun getAccessToken(@Body refreshTokenDTO: RefreshTokenDTO? = null): Response<TokensDTO>

    /**
     * Your GET endpoint
     * Get roles which are in application
     * Responses:
     *  - 200: OK
     *  - 401: Unauthorized
     *  - 403: Forbidden
     *  - 500: Not Found
     *
     * @return [kotlin.collections.List<kotlin.String>]
     */
    @GET("api/auth/roles")
    suspend fun getRoles(): Response<kotlin.collections.List<kotlin.String>>

    /**
     *
     * Login to the application with username and password
     * Responses:
     *  - 200: OK
     *  - 401: Unauthorized
     *  - 500: Created
     *
     * @param loginRequestDTO  (optional)
     * @return [LoginResponseDTO]
     */
    @POST("api/auth/login")
    suspend fun login(@Body loginRequestDTO: LoginRequestDTO? = null): Response<LoginResponseDTO>

    /**
     *
     * Setting password for user by admin
     * Responses:
     *  - 200: OK
     *  - 400: Bad Request
     *  - 401: Unauthorized
     *  - 403: Forbidden
     *  - 404: Not Found
     *  - 500: Internal Server Error
     *
     * @param userId
     * @param passwordChangeDTO  (optional)
     * @return [Unit]
     */
    @PUT("api/auth/passwordset/{userId}")
    suspend fun setPasswordForUser(
        @Path("userId") userId: kotlin.String,
        @Body passwordChangeDTO: PasswordChangeDTO? = null
    ): Response<Unit>

}
