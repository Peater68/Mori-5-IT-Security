package hu.bme.caffshare.data.network.interceptor

import com.squareup.moshi.Moshi
import hu.bme.caffshare.data.local.TokenDataSource
import hu.bme.caffshare.data.network.NetworkModule
import hu.bme.caffshare.data.network.model.RefreshTokenDTO
import hu.bme.caffshare.data.network.model.TokensDTO
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import timber.log.Timber

class RefreshTokenInterceptor(
    private val tokenDataSource: TokenDataSource,
    private val moshi: Moshi
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val interceptedRequest = chain.request()
        val interceptedResponse = chain.proceed(interceptedRequest)

        if (interceptedResponse.code == 401) {
            val refreshToken = tokenDataSource.refreshToken

            if (refreshToken != null) {
                val accessTokenRequest = buildAccessTokenRequest(refreshToken)
                val accessTokenResponse = chain.proceed(accessTokenRequest)

                if (accessTokenResponse.code == 200) {
                    val bodyString = accessTokenResponse.body!!.string()
                    val tokensDTO = moshi.adapter(TokensDTO::class.java).fromJson(bodyString)!!

                    tokenDataSource.saveTokens(tokensDTO)

                    return chain.proceed(interceptedRequest.newBuilder().build())
                } else {
                    Timber.e("Error while retrieving access token with refresh token!")
                }
            } else {
                Timber.e("No refresh token found!")
            }
        }
        return interceptedResponse
    }

    private fun buildAccessTokenRequest(refreshToken: String): Request {
        val endpoint = NetworkModule.BASE_URL + "/api/auth/token"

        val content = moshi
            .adapter(RefreshTokenDTO::class.java)
            .toJson(RefreshTokenDTO(refreshToken))
            .toByteArray()

        val body = content.toRequestBody("application/json".toMediaTypeOrNull(), 0, content.size)

        return Request.Builder()
            .url(endpoint)
            .method("POST", body)
            .build()
    }
}