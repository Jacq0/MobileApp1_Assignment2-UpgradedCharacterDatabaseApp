package org.assignment.characterapplication.adapters

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.assignment.characterapplication.R
import org.assignment.characterapplication.databinding.CardCharacterBinding
import org.assignment.characterapplication.main.Main
import org.assignment.characterapplication.models.CharacterModel
import org.assignment.characterapplication.models.UserModel

interface CharacterListener {
    fun onCharacterClick(character: CharacterModel)
}

class CharacterAdapter constructor(private val app: Main,private val selectedCharacters: List<CharacterModel>,
                                   private val listener: CharacterListener) :
    RecyclerView.Adapter<CharacterAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardCharacterBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding, app.currentUser)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val character = selectedCharacters[holder.adapterPosition]
        holder.bind(character, listener)
    }

    override fun getItemCount(): Int = selectedCharacters.size

    class MainHolder(private val binding : CardCharacterBinding, val user: UserModel) :
        RecyclerView.ViewHolder(binding.root) {

        fun checkIfFavourite(character: CharacterModel): Boolean
        {
            if(user.favourites.contains(character.id))
            {
                return true
            }
            return false
        }

        fun bind(character: CharacterModel, listener: CharacterListener) {
            binding.name.text = character.name
            //set the character name to bold if they are favourited.
            if(checkIfFavourite(character))
            {
                binding.name.setTypeface(Typeface.DEFAULT_BOLD)
            }
            Picasso.get().load(character.image).into(binding.characterImage)
            binding.root.setOnClickListener { listener.onCharacterClick(character) }
        }
    }
}