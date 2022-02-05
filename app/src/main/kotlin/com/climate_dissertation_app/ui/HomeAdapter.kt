package com.climate_dissertation_app.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.climate_dissertation_app.R
import kotlinx.android.synthetic.main.activity_home.view.*

class HomeAdapter : RecyclerView.Adapter<CustomViewHolder>() {

    private val tabTitles = listOf("Outfits", "Wardrobe", "Garments", "Accessories")

    override fun getItemCount(): Int {
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.activity_home, parent, false)
        return CustomViewHolder(cellForRow)

    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val tabLabels = tabTitles[position]
        holder.view.mainScreenTitle.text = tabLabels
    }
}

class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {


}