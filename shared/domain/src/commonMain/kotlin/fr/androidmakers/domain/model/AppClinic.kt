package fr.androidmakers.domain.model

fun Session.isAppClinic(): Boolean {
  return tags.contains("App Clinic")
}

const val APPLY_FOR_APP_CLINIC_MAILTO_URL = "mailto:app.clinic@paug.fr?subject=App-Clinic%20-%20%5BYOUR_APP%5D&body=Hi%20Android%20Makers%2C%0A%0AI'm%20interested%20in%20participating%20in%20the%20App-Clinic.%20Below%20are%20the%20details%20of%20my%20app%3A%0A%0A%20%20%20%20Name%3A%0A%20%20%20%20Short%20Description%3A%0A%20%20%20%20Company%3A%0A%20%20%20%20Store%20URL%20(or%20link%20to%20an%20APK%20file)%3A%0A%20%20%20%20Requirements%3A%0A%20%20%20%20Anything%20Else%3F%3A%0A%0A%0ABest%2C%0A%5BYour%20Name%5D"