package fr.androidmakers.store.graphql

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

internal fun <T> Flow<T>.toResultFlow(): Flow<Result<T>> = this.map {
  Result.success(it)
}.catch {
  emit(Result.failure<T>(it))
}
