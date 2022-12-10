package org.assignment.characterapplication.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// this model is to store data on the user for the favouriting feature.
// It stores a list of favourite character IDs as well as the users ID
// this is separate from the firebase user, but it uses the Firebase user ID for authentication
@Parcelize
class UserModel(var id: String = "",
                var favourites: MutableList<Long>): Parcelable