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
        val textViewId: TextView = view.findViewById(R.id.character_id)
        val imageViewCharacter: ImageView = view.findViewById(R.id.character_image)
        //Adicionar os outros elementos, conforme character_item.xml
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.character_item, parent, false)
        return CharacterViewHolder(viewItem)
    }

    override fun getItemCount(): Int = characterList.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characterList[position]
        holder.textViewName.text = character.name
        holder.textViewId.text = "#${character.id}"
        // incorreto -> holder.imageViewCharacter.image = character.image
        Glide.with(context).load(character.image).into(holder.imageViewCharacter);
    }
}