package hu.bme.caffshare.data.network

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import hu.bme.caffshare.data.local.TokenDataSource
import hu.bme.caffshare.data.network.api.*
import hu.bme.caffshare.data.network.interceptor.AccessTokenInterceptor
import hu.bme.caffshare.data.network.interceptor.RefreshTokenInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
class NetworkModule {
    companion object {
        const val BASE_URL = "http://10.0.2.2:8080"
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(tokenDataSource: TokenDataSource, moshi: Moshi): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AccessTokenInterceptor(tokenDataSource))
            .addInterceptor(RefreshTokenInterceptor(tokenDataSource, moshi))
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.HEADERS
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create()

    @Provides
    @Singleton
    fun provideCaffApi(retrofit: Retrofit): CaffApi = retrofit.create()

    @Provides
    @Singleton
    fun provideCommentApi(retrofit: Retrofit): CommentApi = retrofit.create()

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create()

    @Provides
    @Singleton
    fun provideTagsApi(retrofit: Retrofit): TagsApi = retrofit.create()
}