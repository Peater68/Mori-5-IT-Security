package hu.bme.caffshare.data.local

import android.content.Context
import javax.inject.Inject

class TokenDataSource @Inject constructor(context: Context) {
    companion object {
        const val ACCESS_TOKEN_HEADER = "Access-Token"
        const val BEARER_PREFIX = "Bearer"
    }

    private val store = TokenKrate(context)

    private var _inMemoryAccessToken: String? = null

    var accessToken: String?
        get() {
            if (_inMemoryAccessToken == null) {
                _inMemoryAccessToken = store.accessToken
            }
            return "$BEARER_PREFIX $_inMemoryAccessToken"
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
}
