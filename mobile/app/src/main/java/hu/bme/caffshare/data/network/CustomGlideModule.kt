package hu.bme.caffshare.data.network

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.Excludes
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import hu.bme.caffshare.CaffShareApplication
import okhttp3.OkHttpClient
import java.io.InputStream
import javax.inject.Inject

@GlideModule
@Excludes(OkHttpLibraryGlideModule::class)
class CustomGlideModule : AppGlideModule() {

    @Inject
    lateinit var okHttpClient: OkHttpClient

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        (context.applicationContext as? CaffShareApplication)
            ?.injector
            ?.inject(this)
            ?: throw IllegalStateException("InjectedActivity should not be used without an Application that inherits from RainbowCakeApplication")

        val factory = OkHttpUrlLoader.Factory(okHttpClient)
        glide.registry.replace(GlideUrl::class.java, InputStream::class.java, factory)
    }
}