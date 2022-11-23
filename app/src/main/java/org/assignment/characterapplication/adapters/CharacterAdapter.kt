package org.assignment.characterapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.assignment.characterapplication.models.CharacterModel
//import org.assignment.characterapplication.databinding.CardCharacterBinding

interface PlacemarkListener {
    fun onPlacemarkClick(placemark: CharacterModel)
}



/*class PlacemarkAdapter constructor(private var placemarks: List<CharacterModel>,
                                   private val listener: PlacemarkListener) :
    RecyclerView.Adapter<PlacemarkAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardCharacterBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val placemark = placemarks[holder.adapterPosition]
        holder.bind(placemark, listener)
    }

    override fun getItemCount(): Int = placemarks.size

    class MainHolder(private val binding : CardPlacemarkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(placemark: PlacemarkModel, listener: PlacemarkListener) {
            binding.placemarkTitle.text = placemark.title
            binding.description.text = placemark.description
            binding.root.setOnClickListener { listener.onPlacemarkClick(placemark) }
        }
    }
}*/