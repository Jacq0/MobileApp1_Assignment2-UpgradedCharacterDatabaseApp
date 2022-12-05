package org.assignment.characterapplication.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.assignment.characterapplication.R
import org.assignment.characterapplication.adapters.CharacterAdapter
import org.assignment.characterapplication.adapters.CharacterListener
import org.assignment.characterapplication.databinding.ActivityCharacterListBinding
import org.assignment.characterapplication.main.Main
import org.assignment.characterapplication.models.CharacterModel
import timber.log.Timber.i

class CharacterListActivity : AppCompatActivity(), CharacterListener
{
    lateinit var app: Main
    private lateinit var binding: ActivityCharacterListBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as Main

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = CharacterAdapter(app.characters.findAll(), this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        when (item.itemId)
        {
            R.id.item_add ->
            {
                val launcherIntent = Intent(this, CharacterEditActivity::class.java)
                getAddResult.launch(launcherIntent)
            }
            R.id.item_logout ->
            {
                Firebase.auth.signOut()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCharacterClick(character: CharacterModel)
    {
        val launcherIntent = Intent(this, CharacterPageActivity::class.java)
        launcherIntent.putExtra("character_select", character)
        getClickResult.launch(launcherIntent)
    }

    private val getAddResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
        if (it.resultCode == Activity.RESULT_OK)
        {
            (binding.recyclerView.adapter)?.notifyItemRangeChanged(0,app.characters.findAll().size)
        }
    }

    private val getClickResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
        if (it.resultCode == Activity.RESULT_OK)
        {
            binding.recyclerView.adapter?.notifyDataSetChanged() //refresh the list
        }
    }
}