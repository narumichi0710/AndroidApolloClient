package com.example.core.apollo.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
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
        val serverUrl = ""
        val okHttpClient = OkHttpClient.Builder().build()
        return ApolloClient.Builder()
            .serverUrl(serverUrl)
            .okHttpClient(okHttpClient)
            .build()
    }
}

