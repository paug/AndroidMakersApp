package fr.paug.androidmakers.util

import android.net.Uri
import androidx.annotation.Nullable

object YoutubeUtil
{

  @JvmStatic
  @Nullable
  fun getVideoID(videoURL:String?):String?
  {
    if (videoURL.isNullOrEmpty())
    {
      return null
    }
    val parse = Uri.parse(videoURL)
    parse.host?.toLowerCase()?.also { host ->
      if (host.equals("youtu.be", ignoreCase = true))
      {
        return parse.lastPathSegment
      }
      else if (host.endsWith("youtube.com"))
      {
        val id = parse.getQueryParameter("v")
        if (id.isNullOrEmpty().not())
        {
          parse.pathSegments
              ?.takeIf { it.size == 2 && it[0].equals("embed", ignoreCase = true) }
              ?.apply {
                return this[1]
              }
        }
        return id
      }
    }

    return null
  }

  @Nullable
  @JvmStatic
  fun getVideoUri(videoURL:String):Uri?
  {
    val videoID = getVideoID(videoURL)
    return if (!videoID.isNullOrBlank())
    {
      Uri.parse("https://youtu.be/$videoID")
    }
    else null
  }

}
