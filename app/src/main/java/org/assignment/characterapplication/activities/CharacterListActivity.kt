package org.assignment.characterapplication.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
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
        binding.toolbar.setTitle(R.string.list_title)
        setSupportActionBar(binding.toolbar)

        app = application as Main

        app.manageUser() //it would be better to do this in the main method, but it causes issues

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = CharacterAdapter(app, app.characters.findAll(), this)

        binding.searchBar.addTextChangedListener(object:TextWatcher {

            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int)
            {
                updateSearch(s.toString())
            }
        })

        binding.filterSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                updateSearch(binding.searchBar.text.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
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
            R.id.item_random ->
            {
                if(getRandomCharacter() != null)
                {
                    onCharacterClick(getRandomCharacter()!!) //get a random character from the list and take the user to the page
                }
                else
                {
                    i("No Characters Found")
                }
            }
            R.id.item_logout ->
            {
                Firebase.auth.signOut()
                setResult(RESULT_OK)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun getRandomCharacter(): CharacterModel?
    {
        return app.characters.findAll().randomOrNull()
    }

    fun updateSearch(name: String)
    {
        val spinnerVal = findViewById<Spinner>(R.id.filter_spinner).selectedItem.toString()

        binding.recyclerView.adapter = CharacterAdapter(app, app.characters.filterBy(spinnerVal, app.characters.findByString(name), app.currentUser), this)
        binding.recyclerView.adapter?.notifyDataSetChanged() //refresh the list
        (binding.recyclerView.adapter)?.notifyItemRangeChanged(0,app.characters.findByString(name).size)
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
            (binding.recyclerView.adapter)?.notifyItemRangeChanged(0,app.characters.findAll().size) //this fixes the unfavouriting issue
            binding.recyclerView.adapter?.notifyDataSetChanged() //refresh the list
        }
    }
}