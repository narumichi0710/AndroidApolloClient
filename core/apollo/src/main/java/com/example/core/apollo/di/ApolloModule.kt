package com.example.core.apollo.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.example.core.apollo.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApolloModule {
    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        val serverUrl = "https://api.github.com/graphql"
        val okHttpClient = OkHttpClient.Builder().addInterceptor {
            val request = it.request().newBuilder()
                .addHeader(
                    "Authorization",
                    "Bearer ${BuildConfig.GITHUB_TOKEN}"
                )
                .build()
            it.proceed(request)
        }.build()
        return ApolloClient.Builder()
            .serverUrl(serverUrl)
            .okHttpClient(okHttpClient)
            .build()
    }
}

