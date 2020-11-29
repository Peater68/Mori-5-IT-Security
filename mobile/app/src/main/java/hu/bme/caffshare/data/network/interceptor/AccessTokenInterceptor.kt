package hu.bme.caffshare.data.network.interceptor

import hu.bme.caffshare.data.local.TokenDataSource
import okhttp3.Interceptor
import okhttp3.Response

class AccessTokenInterceptor(private val tokenDataSource: TokenDataSource) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newRequest =
            tokenDataSource.accessToken?.let { accessToken ->
                request.newBuilder()
                    .addHeader(
                        TokenDataSource.ACCESS_TOKEN_HEADER,
                        "${TokenDataSource.BEARER_PREFIX} $accessToken"
                    )
                    .build()
            } ?: request

        return chain.proceed(newRequest)
    }
}