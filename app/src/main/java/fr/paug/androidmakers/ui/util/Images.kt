package fr.paug.androidmakers.ui.util

internal fun imageUrl(relativeUrl: String): String {
  return "https://raw.githubusercontent.com/paug/android-makers-2022/main/$relativeUrl"
      .replace("..", "")
      .replace(".svg", ".svg.png")
      .let {
        if (it.endsWith(".svg.png")) {
          it.replace("logos/", "logos/pngs/")
        } else {
          it
        }
      }

}