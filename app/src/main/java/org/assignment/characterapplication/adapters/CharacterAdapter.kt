package org.assignment.characterapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.assignment.characterapplication.databinding.CardCharacterBinding
import org.assignment.characterapplication.models.CharacterModel

interface CharacterListener {
    fun onCharacterClick(character: CharacterModel)
}

class CharacterAdapter constructor(private var characters: List<CharacterModel>,
                                   private val listener: CharacterListener) :
    RecyclerView.Adapter<CharacterAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardCharacterBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val character = characters[holder.adapterPosition]
        holder.bind(character, listener)
    }

    override fun getItemCount(): Int = characters.size

    class MainHolder(private val binding : CardCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: CharacterModel, listener: CharacterListener) {
            binding.name.text = character.name
            binding.description.text = character.description
            binding.root.setOnClickListener { listener.onCharacterClick(character) }
        }
    }
}