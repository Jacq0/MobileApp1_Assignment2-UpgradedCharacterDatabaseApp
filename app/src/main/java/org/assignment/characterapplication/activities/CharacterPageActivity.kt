package org.assignment.characterapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.assignment.characterapplication.R
import org.assignment.characterapplication.databinding.ActivityCharacterPageBinding
import org.assignment.characterapplication.helpers.showImagePicker
import org.assignment.characterapplication.main.Main
import org.assignment.characterapplication.models.CharacterModel
import timber.log.Timber

class CharacterPageActivity : AppCompatActivity() {

    lateinit var app: Main
    private lateinit var binding: ActivityCharacterPageBinding
    var character = CharacterModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCharacterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        character = intent.extras?.getParcelable("character_select")!!

        binding.toolbar.title = character.name

        app = application as Main

        binding.descriptionTag.setText(R.string.header_description)
        binding.description.setText(character.description)
        binding.originalAppearanceTag.setText(R.string.header_originalAppearance)
        binding.originalAppearance.setText(character.originalAppearance + " (" + character.originalAppearanceYear + ")")
        Picasso.get().load(character.image).into(binding.characterImage)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}