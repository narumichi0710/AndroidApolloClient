package com.example.core.apollo

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Query
import com.apollographql.apollo3.cache.normalized.watch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart


sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Failure(val throwable: Throwable) : Result<Nothing>
}

sealed interface UIState<out T> {
    data object Uninitialized : UIState<Nothing>
    data object Loading : UIState<Nothing>
    data class Success<T>(val data: T) : UIState<T>
    data class Error(val throwable: Throwable) : UIState<Nothing>
}

suspend fun <D : Query.Data> ApolloClient.executeQuery(
    query: Query<D>
): Result<D> {
    return try {
        val response = query(query)
            .execute()
        val data = response.data
        if (response.hasErrors() || data == null) {
            Result.Failure(Exception(""))
        } else {
            Result.Success(data)
        }
    } catch (e: Exception) {
        Result.Failure(e)
    }
}

fun <D : Query.Data> ApolloClient.watchQuery(
    query: Query<D>,
    initialData: D? = null
): Flow<UIState<D>> = query(query)
    .watch(initialData)
    .map { response ->
        val data = response.data
        if (response.hasErrors() || data == null) {
            UIState.Error(Exception())
        } else {
            UIState.Success(data)
        }
    }
    .onStart { emit(UIState.Loading) }
    .catch {
        emit(UIState.Error(it))
    }