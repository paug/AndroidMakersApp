package fr.androidmakers.domain

actual abstract class PlatformContext private constructor() {
  companion object {
    val INSTANCE = object : PlatformContext() {}
  }
}
