package com.climate_dissertation_app.ui.mainscreen

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.climate_dissertation_app.R
import com.climate_dissertation_app.viewmodel.ClothItem
import com.climate_dissertation_app.viewmodel.ClothesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_current_clothes.*
import javax.inject.Inject

const val recommendedClothesKey = "recommendedClothes"


@AndroidEntryPoint
class CurrentClothesFragment @Inject constructor() : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clothes_list_view.addItemDecoration(ItemSeparation())
        val recommendedClothes =
            arguments?.getParcelableArrayList<ClothItem>(recommendedClothesKey) ?: emptyList()
        clothes_list_view.adapter = ClothesAdapter(recommendedClothes)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_clothes, container, false)
    }
}

class ItemSeparation(private val margin: Int = 50) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = margin
    }
}