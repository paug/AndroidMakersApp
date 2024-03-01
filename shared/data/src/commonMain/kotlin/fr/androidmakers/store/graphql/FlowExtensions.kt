package fr.androidmakers.store.graphql

import at.asitplus.KmmResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

internal fun <T> Flow<T>.toResultFlow(): Flow<KmmResult<T>> = this.map {
  KmmResult.success(it)
}.catch {
  emit(KmmResult.failure<T>(it))
}
