package com.climate_dissertation_app.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.climate_dissertation_app.R
import com.squareup.picasso.Picasso

class ClothesAdapter(
    private val clothes: List<ClothItem>
) : RecyclerView.Adapter<ClothesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClothesViewHolder =
        ClothesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cloth_item_layout, parent, false)
        )


    override fun onBindViewHolder(holder: ClothesViewHolder, position: Int) {
        holder.clothItemImageView.contentDescription = clothes[position].name
        Picasso.get()
            .load(clothes[position].resourceId)
            .into(holder.clothItemImageView)
    }

    override fun getItemCount(): Int = clothes.size
}

class ClothesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val clothItemImageView: ImageView = view.findViewById(R.id.cloth_item)
}