package com.oceanbrasil.rickandmortyapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oceanbrasil.rickandmortyapp.domain.Character

class CharacterAdapter(private val context: Context
                       , private val characterList: List<Character>) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView = view.findViewById(R.id.character_name)
        // val textViewId: TextView = view.findViewById(R.id.character_id)
        val imageViewCharacter: ImageView = view.findViewById(R.id.character_image)
        val statusIndicatorView: View = view.findViewById(R.id.character_status_indicator)
        val textViewStatus: TextView = view.findViewById(R.id.character_status_and_species)
        val textViewLocation: TextView = view.findViewById(R.id.character_last_known_location)
        val textViewFirstSeen: TextView = view.findViewById(R.id.character_first_seen)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.character_item2, parent, false)
        return CharacterViewHolder(viewItem)
    }

    override fun getItemCount(): Int = characterList.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characterList[position]
        holder.textViewName.text = character.name
        // holder.textViewId.text = "#${character.id}"
        Glide.with(context).load(character.image).into(holder.imageViewCharacter);
        holder.textViewStatus.text = "${character.status} - ${character.species}"
        val statusColor = when (character.status) {
            "Alive" -> R.drawable.status_alive
            "Dead" -> R.drawable.status_dead
            else -> R.drawable.status_unknown
        }
        holder.statusIndicatorView.setBackgroundResource(
            statusColor
        )
        holder.textViewLocation.text = character.location.name
        val episodeNum = character.episode[0].substringAfterLast("/")
        holder.textViewFirstSeen.text = "Episode #${episodeNum}"
    }
}