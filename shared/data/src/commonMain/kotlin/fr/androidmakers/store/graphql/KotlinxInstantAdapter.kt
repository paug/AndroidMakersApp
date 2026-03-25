package fr.androidmakers.store.graphql

import com.apollographql.apollo.api.Adapter
import com.apollographql.apollo.api.CustomScalarAdapters
import com.apollographql.apollo.api.json.JsonReader
import com.apollographql.apollo.api.json.JsonWriter
import kotlinx.datetime.Instant

val KotlinxInstantAdapter = object : Adapter<Instant> {
  override fun fromJson(reader: JsonReader, customScalarAdapters: CustomScalarAdapters): Instant {
    val str = reader.nextString() ?: throw IllegalStateException("Expected non-null Instant string")
    return Instant.parse(str)
  }

  override fun toJson(writer: JsonWriter, customScalarAdapters: CustomScalarAdapters, value: Instant) {
    writer.value(value.toString())
  }
}
