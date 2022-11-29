package org.assignment.characterapplication.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.assignment.characterapplication.R
import org.assignment.characterapplication.databinding.ActivityCharacterEditBinding
import org.assignment.characterapplication.helpers.showImagePicker
import org.assignment.characterapplication.main.Main
import org.assignment.characterapplication.models.CharacterModel
import timber.log.Timber.i

class CharacterEditActivity: AppCompatActivity()
{
    private lateinit var binding: ActivityCharacterEditBinding
    var character = CharacterModel()
    lateinit var app: Main
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>
    val IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        var edit = false

        binding = ActivityCharacterEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = R.string.button_addCharacter.toString();
        setSupportActionBar(binding.toolbarAdd)

        app = application as Main

        if(intent.hasExtra("character_edit"))
        {
            edit = true
            binding.toolbarAdd.title = R.string.app_editCharacterPage.toString();
            character = intent.extras?.getParcelable("character_edit")!!
            binding.characterName.setText(character.name)
            binding.description.setText(character.description)
            binding.originalAppearance.setText(character.originalAppearance)
            binding.originalAppearanceYear.setText(character.originalAppearanceYear.toString())
            binding.btnAdd.setText(R.string.button_updateCharacter)
            Picasso.get().load(character.image).into(binding.characterImage)
        }

        binding.btnAdd.setOnClickListener() {
            character.name = binding.characterName.text.toString()
            character.description = binding.description.text.toString()
            character.originalAppearance = binding.originalAppearance.text.toString()
            character.originalAppearanceYear = Integer.valueOf(binding.originalAppearanceYear.text.toString())
            if (character.name.isEmpty()) {
                Snackbar.make(it,R.string.error_fillFields, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.characters.update(character.copy())
                } else {
                    app.characters.create(character.copy())
                }
            }
            i("add Button Pressed: $character")
            setResult(RESULT_OK)
            finish()
        }

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher, this)
        }

        registerImagePickerCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_character_edit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
            R.id.item_delete -> {
                //delete item from existance
                app.characters.delete(character);
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")

                            val image = result.data!!.data!!
                            contentResolver.takePersistableUriPermission(image,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            character.image = image

                            Picasso.get()
                                .load(character.image)
                                .into(binding.characterImage)
                            binding.chooseImage.setText(R.string.select_characterImage)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}
