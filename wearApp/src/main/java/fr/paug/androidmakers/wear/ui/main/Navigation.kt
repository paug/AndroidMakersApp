package fr.paug.androidmakers.wear.ui.main

object Navigation {
  const val main = "main"

  const val signIn = "signIn"

  const val id = "id"
  const val sessionDetail = "sessionDetail/{$id}"
  fun sessionDetail(id: String): String {
    return "sessionDetail/$id"
  }
}
