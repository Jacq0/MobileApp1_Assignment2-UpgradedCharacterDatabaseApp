package org.assignment.characterapplication.models

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterModel(var id: Long = 0,
                          var name: String = "",
                            var description: String = "",
                            var originalAppearance: String = "",
                            var originalAppearanceYear: Int = 0,
                            var image: Uri = Uri.EMPTY) : Parcelable