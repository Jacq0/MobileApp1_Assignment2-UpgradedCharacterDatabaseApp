package org.assignment.characterapplication.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.CompoundButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import org.assignment.characterapplication.R
import org.assignment.characterapplication.databinding.ActivityCharacterPageBinding
import org.assignment.characterapplication.main.Main
import org.assignment.characterapplication.models.CharacterModel
import timber.log.Timber.i


class CharacterPageActivity : AppCompatActivity()
{
    lateinit var app: Main
    private lateinit var binding: ActivityCharacterPageBinding
    var character = CharacterModel()
    var isFavourited = false

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivityCharacterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.favouriteToggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                updateFavouriteStatus(isChecked)
                i("FAVOURITED")
            } else {
                updateFavouriteStatus(isChecked)
                i("UNFAVOURITED")
            }
        }

        character = intent.extras?.getParcelable("character_select")!!

        updateValues()

        setSupportActionBar(binding.toolbar)

        app = application as Main

        //check if this character is favourited by the user
        if(app.currentUser.favourites.contains(character.id))
        {
            isFavourited = true
            binding.favouriteToggle.setText(R.string.button_unfavourite)
            binding.favouriteToggle.isChecked = isFavourited
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean
    {
        menuInflater.inflate(R.menu.menu_character_page, menu)
        //disable edit button if user isn't the one who created
        if(character.createdByUserID != Firebase.auth.currentUser!!.uid)
        {
            menu?.findItem(R.id.item_edit)?.setEnabled(false);
        }
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        when (item.itemId)
        {
            R.id.item_cancel ->
            {
                setResult(RESULT_OK)
                finish()
            }
            R.id.item_edit ->
            {
                val launcherIntent = Intent(this, CharacterEditActivity::class.java)
                launcherIntent.putExtra("character_edit", character)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateFavouriteStatus(status: Boolean)
    {
        isFavourited = status

        if(status && !app.currentUser.favourites.contains(character.id))
        {
            //add this to the users favourited list
            binding.favouriteToggle.setText(R.string.button_unfavourite)
            app.currentUser.favourites.add(character.id)
            app.users.update(app.currentUser)
        }
        if(!status && app.currentUser.favourites.contains(character.id))
        {
            //remove from users favourite list
            binding.favouriteToggle.setText(R.string.button_favourite)
            app.currentUser.favourites.remove(character.id)
            app.users.update(app.currentUser)
        }
    }

    private fun updateValues()
    {
        binding.toolbar.title = character.name

        binding.descriptionTag.setText(R.string.header_description)
        binding.description.setText(character.description)
        binding.originalAppearanceTag.setText(R.string.header_originalAppearance)
        binding.originalAppearance.setText(character.originalAppearance + " (" + character.originalAppearanceYear + ")")
        Picasso.get().load(character.image).into(binding.characterImage)
    }

    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {
            if (it.resultCode == Activity.RESULT_OK)
            {
                character = it.data!!.getParcelableExtra("char_result")!!
                updateValues()
            }
        }
}