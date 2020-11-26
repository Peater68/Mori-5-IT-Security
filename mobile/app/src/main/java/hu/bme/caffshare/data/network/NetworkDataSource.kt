package hu.bme.caffshare.data.network

import android.content.Context
import android.net.Uri
import hu.bme.caffshare.data.local.TokenDataSource
import hu.bme.caffshare.data.network.api.AuthApi
import hu.bme.caffshare.data.network.api.CaffApi
import hu.bme.caffshare.data.network.api.CommentApi
import hu.bme.caffshare.data.network.api.UserApi
import hu.bme.caffshare.data.network.model.LoginRequestDTO
import hu.bme.caffshare.data.network.model.LoginResponseDTO
import hu.bme.caffshare.data.network.model.PasswordChangeDTO
import hu.bme.caffshare.data.network.model.UserRegistrationDTO
import hu.bme.caffshare.domain.model.DomainCaffDetails
import hu.bme.caffshare.domain.model.DomainCaffSum
import hu.bme.caffshare.domain.model.DomainUser
import hu.bme.caffshare.domain.model.toDomainModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class NetworkDataSource @Inject constructor(
    private val authApi: AuthApi,
    private val caffApi: CaffApi,
    private val commentApi: CommentApi,
    private val userApi: UserApi,
    private val tokenDataSource: TokenDataSource,
    private val context: Context
) {

    // region Auth

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

    // endregion

    // region User

    suspend fun getCurrentUserProfile(): DomainUser? {
        val response = userApi.getMe()

        return response.body()?.toDomainModel()
    }

    // endregion

    // region Caff

    suspend fun deleteCaffById(caffId: String): Boolean {
        val response = caffApi.deleteCaffById(caffId)

        return response.isSuccessful
    }

    // TODO: handle returned file
    suspend fun downloadPreviewOrCaffFile(caffId: String, type: CaffDownloadType) {
        val response = caffApi.downloadPreviewOrCaffFile(caffId, type.name).body()
    }

    suspend fun getCaffFiles(): List<DomainCaffSum>? {
        val response = caffApi.getAllCaffs()

        return response.body()?.map { it.toDomainModel() }
    }

    suspend fun getCaffFileDetails(caffId: String): DomainCaffDetails? {
        val response = caffApi.getCaffDetailsById(caffId)

        return response.body()?.toDomainModel()
    }

    suspend fun uploadCaffFile(caffFileUri: Uri): Boolean {
        val formDataName = "caffFile"
        val body = createMultipartBodyFromUri(caffFileUri, formDataName)
        val response = caffApi.uploadCaff(body)

        return response.isSuccessful
    }

    private fun createMultipartBodyFromUri(
        caffFileUri: Uri,
        formDataName: String
    ): MultipartBody.Part {
        val caffFile = File(caffFileUri.path!!)
        val requestFile = caffFile.asRequestBody(
            context.contentResolver.getType(caffFileUri)!!.toMediaTypeOrNull()
        )
        return MultipartBody.Part.createFormData(formDataName, caffFile.name, requestFile)
    }

    // endregion

    // region Comment

    // endregion
}

enum class CaffDownloadType {
    PREVIEW, CAFF
}