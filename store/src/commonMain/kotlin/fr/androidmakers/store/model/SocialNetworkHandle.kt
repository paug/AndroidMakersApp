package fr.androidmakers.store.model

class SocialNetworkHandle(name: String?, link: String?) {
  enum class SocialNetworkType {
    Unknown, Twitter, Facebook, Website, Github;
  }

  val networkType: SocialNetworkType
  val link: String?

  companion object {
    private fun getSocialNetworkType(networkName: String?): SocialNetworkType {
      if (!networkName.isNullOrBlank()) {
        if (networkName.equals("twitter", ignoreCase = true)) {
          return SocialNetworkType.Twitter
        } else if (networkName.equals("github", ignoreCase = true)) {
          return SocialNetworkType.Github
        } else if (networkName.equals("site", ignoreCase = true)) {
          return SocialNetworkType.Website
        } else if (networkName.equals("facebook", ignoreCase = true)) {
          return SocialNetworkType.Facebook
        }
      }
      return SocialNetworkType.Unknown
    }
  }

  init {
    networkType = getSocialNetworkType(name)
    this.link = link
  }
}