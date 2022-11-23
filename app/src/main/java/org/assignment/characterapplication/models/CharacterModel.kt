package org.assignment.characterapplication.models

import android.net.Uri

data class CharacterModel(var id: Long = 0,
                          var name: String = "",
                            var description: String = "",
                            var originalAppearance: String = "",
                            var originalAppearanceYear: Int = 0,
                            var image: Uri = Uri.EMPTY)