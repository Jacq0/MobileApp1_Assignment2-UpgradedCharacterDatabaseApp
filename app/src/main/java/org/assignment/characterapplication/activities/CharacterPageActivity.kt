package org.assignment.characterapplication.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import org.assignment.characterapplication.R
import org.assignment.characterapplication.adapters.CharacterAdapter
import org.assignment.characterapplication.adapters.CharacterListener
import org.assignment.characterapplication.databinding.ActivityCharacterPageBinding
import org.assignment.characterapplication.main.Main
import org.assignment.characterapplication.models.CharacterModel

class CharacterPageActivity : AppCompatActivity()

{
    lateinit var app: Main
    private lateinit var binding: ActivityCharacterPageBinding
    var character = CharacterModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCharacterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        character = intent.extras?.getParcelable("character_select")!!

        binding.toolbar.title = character.name

        setSupportActionBar(binding.toolbar)

        app = application as Main

        binding.descriptionTag.setText(R.string.header_description)
        binding.description.setText(character.description)
        binding.originalAppearanceTag.setText(R.string.header_originalAppearance)
        binding.originalAppearance.setText(character.originalAppearance + " (" + character.originalAppearanceYear + ")")
        Picasso.get().load(character.image).into(binding.characterImage)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_character_page, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
            R.id.item_edit -> {
                val launcherIntent = Intent(this, CharacterEditActivity::class.java)
                launcherIntent.putExtra("character_edit", character)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.notifyItemRangeChanged(0,app.characters.findAll().size)
            }
        }
}