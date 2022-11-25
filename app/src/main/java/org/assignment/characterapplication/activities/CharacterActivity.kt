package org.assignment.characterapplication.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import org.assignment.characterapplication.R
import org.assignment.characterapplication.databinding.ActivityCharacterBinding
import org.assignment.characterapplication.main.Main
import org.assignment.characterapplication.models.CharacterModel
import timber.log.Timber.i

class CharacterActivity: AppCompatActivity()
{
    private lateinit var binding: ActivityCharacterBinding
    var character = CharacterModel()
    lateinit var app: Main
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>
    val IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        var edit = false

        binding = ActivityCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as Main

        i("Character Database Application Started")

        if(intent.hasExtra("character_edit"))
        {
            edit = true
            character = intent.extras?.getParcelable("character_edit")!!
            binding.characterName.setText(character.name)
            binding.description.setText(character.description)
            binding.originalAppearance.setText(character.originalAppearance)
            binding.originalAppearanceYear.setText(character.originalAppearanceYear)
            binding.btnAdd.setText(R.string.button_updateCharacter)
            Picasso.get().load(character.image).into(binding.characterImage)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_character, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {
            result ->
            when(result.resultCode){
                RESULT_OK -> {
                    if (result.data != null) {
                        i("Got Result ${result.data!!.data}")
                        character.image = result.data!!.data!!
                        Picasso.get().load(character.image).into(binding.characterImage)
                    }
                }
                RESULT_CANCELED -> { } else -> { }
            }
        }
    }
}
