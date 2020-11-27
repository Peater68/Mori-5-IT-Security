package hu.bme.caffshare.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import hu.autsoft.krate.Krate
import hu.autsoft.krate.stringPref


class TokenKrate(context: Context) : Krate {
    override val sharedPreferences: SharedPreferences

    init {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        sharedPreferences = EncryptedSharedPreferences.create(
            "secret_shared_pref",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    companion object {
        private const val REFRESH_TOKEN_KEY = "REFRESH_TOKEN"
        private const val ACCESS_TOKEN_KEY = "ACCESS_TOKEN_KEY"
    }

    var accessToken: String? by stringPref(ACCESS_TOKEN_KEY)
    var refreshToken: String? by stringPref(REFRESH_TOKEN_KEY)
}