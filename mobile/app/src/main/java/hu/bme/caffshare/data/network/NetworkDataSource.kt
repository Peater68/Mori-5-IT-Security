package hu.bme.caffshare.data.network

import android.content.Context
import android.net.Uri
import hu.bme.caffshare.data.local.TokenDataSource
import hu.bme.caffshare.data.network.api.AuthApi
import hu.bme.caffshare.data.network.api.CaffApi
import hu.bme.caffshare.data.network.api.CommentApi
import hu.bme.caffshare.data.network.api.UserApi
import hu.bme.caffshare.data.network.model.*
import hu.bme.caffshare.domain.model.*
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

    suspend fun banUser(userId: String): Boolean {
        val response = userApi.banUserById(userId)

        return response.isSuccessful
    }

    suspend fun deleteCurrentUser(): Boolean {
        val response = userApi.deleteMe()

        return response.isSuccessful
    }

    suspend fun getAllUsers(): List<DomainUser>? {
        val response = userApi.getUsers()

        return response.body()?.map { it.toDomainModel() }
    }

    suspend fun makeUserAdmin(userId: String): Boolean {
        val response = userApi.makeUserAdmin(userId)

        return response.isSuccessful
    }

    suspend fun updateCurrentUser(
        firstName: String,
        lastName: String,
        username: String,
        email: String,
    ): Boolean {
        val body = UserUpdateDTO(
            firstName = firstName,
            lastName = lastName,
            username = username,
            email = email,
        )

        val response = userApi.updateMe(body)

        return response.isSuccessful
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

    suspend fun deleteComment(commentId: String): Boolean {
        val response = commentApi.deleteComment(commentId)

        return response.isSuccessful
    }

    suspend fun getCommentsForCaff(caffId: String): List<DomainComment>? {
        val response = commentApi.getComments(caffId)

        return response.body()?.map { it.toDomainModel() }
    }

    suspend fun addComment(caffId: String, comment: String): Boolean {
        val body = CommentUploadDTO(comment)
        val response = commentApi.postComment(caffId, body)

        return response.isSuccessful
    }

    // endregion
}
