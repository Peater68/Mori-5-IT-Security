package hu.bme.caffshare.data.local

import android.content.Context
import hu.bme.caffshare.data.network.model.TokensDTO
import javax.inject.Inject

class TokenDataSource @Inject constructor(context: Context) {
    companion object {
        const val ACCESS_TOKEN_HEADER = "Authorization"
        const val BEARER_PREFIX = "Bearer"
    }

    private val store = TokenKrate(context)

    private var _inMemoryAccessToken: String? = null

    var accessToken: String?
        get() {
            if (_inMemoryAccessToken == null) {
                _inMemoryAccessToken = store.accessToken
            }
            return _inMemoryAccessToken
        }
        set(value) {
            _inMemoryAccessToken = value
            store.accessToken = value
        }

    var refreshToken: String?
        get() = store.refreshToken
        set(value) {
            store.refreshToken = value
        }

    fun saveTokens(tokensDTO: TokensDTO) {
        accessToken = tokensDTO.accessToken
        refreshToken = tokensDTO.refreshToken
    }

    fun removeTokens() {
        accessToken = null
        refreshToken = null
    }
}

