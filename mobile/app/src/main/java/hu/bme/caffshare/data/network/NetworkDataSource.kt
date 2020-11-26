package hu.bme.caffshare.data.network

import hu.bme.caffshare.data.local.TokenDataSource
import hu.bme.caffshare.data.network.api.AuthApi
import hu.bme.caffshare.data.network.api.CaffApi
import hu.bme.caffshare.data.network.api.CommentApi
import hu.bme.caffshare.data.network.api.UserApi
import hu.bme.caffshare.data.network.model.LoginRequestDTO
import hu.bme.caffshare.data.network.model.LoginResponseDTO
import hu.bme.caffshare.data.network.model.PasswordChangeDTO
import hu.bme.caffshare.data.network.model.UserRegistrationDTO
import hu.bme.caffshare.domain.model.DomainUser
import hu.bme.caffshare.domain.model.toDomainModel
import javax.inject.Inject

class NetworkDataSource @Inject constructor(
    private val authApi: AuthApi,
    private val caffApi: CaffApi,
    private val commentApi: CommentApi,
    private val userApi: UserApi,
    private val tokenDataSource: TokenDataSource
) {

    suspend fun login(username: String, password: String): Boolean {
        val body = LoginRequestDTO(
            username = username,
            password = password,
        )

        val response = authApi.login(body)

        val isLoginSuccessful = response.isSuccessful

        if (isLoginSuccessful) {
            saveTokens(response.body()!!)
        }

        return isLoginSuccessful
    }

    private fun saveTokens(tokens: LoginResponseDTO) {
        tokenDataSource.saveTokens(tokens.tokens)
    }

    suspend fun register(
        username: String,
        password: String,
        email: String,
        firstName: String,
        lastName: String
    ): Boolean {
        val body = UserRegistrationDTO(
            username = username,
            password = password,
            email = email,
            firstName = firstName,
            lastName = lastName,
        )

        val response = userApi.createUser(body)

        return response.isSuccessful
    }

    suspend fun changePassword(currentPassword: String, newPassword: String): Boolean {
        val body = PasswordChangeDTO(
            newPassword = newPassword,
            currentPassword = currentPassword
        )

        val response = authApi.changePassword(body)

        return response.isSuccessful
    }

    suspend fun getCurrentUserProfile(): DomainUser? {
        val response = userApi.getMe()

        return response.body()?.toDomainModel()
    }
}