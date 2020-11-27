package hu.bme.caffshare.data.network.api

import hu.bme.caffshare.data.network.model.LoginRequestDTO
import hu.bme.caffshare.data.network.model.LoginResponseDTO
import hu.bme.caffshare.data.network.model.PasswordChangeDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

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
}
