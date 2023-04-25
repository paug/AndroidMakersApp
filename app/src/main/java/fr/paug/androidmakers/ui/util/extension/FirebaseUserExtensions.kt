package fr.paug.androidmakers.ui.util.extension

import com.google.firebase.auth.FirebaseUser
import fr.paug.androidmakers.ui.AMUser

fun FirebaseUser.toAMUser(): AMUser = AMUser(this)
