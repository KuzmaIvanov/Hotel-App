package com.example.hotelapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Room
import com.example.hotelapp.databinding.ItemRoomBinding
import com.google.android.material.chip.ChipGroup

class RoomsItemAdapter(
    var rooms: List<Room>,
    val initPeculiaritiesChipGroup: (ChipGroup, List<String>) -> Unit,
    val initCarousel: (RecyclerView, List<String>) -> Unit,
    val onSelectRoom: () -> Unit
) : RecyclerView.Adapter<RoomsItemAdapter.RoomsItemViewHolder>() {

    class RoomsItemViewHolder(
        val binding: ItemRoomBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomsItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRoomBinding.inflate(inflater, parent, false)
        binding.selectRoomButton.setOnClickListener {
            onSelectRoom()
        }
        return RoomsItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return rooms.size
    }

    override fun onBindViewHolder(holder: RoomsItemViewHolder, position: Int) {
        val room = rooms[position]
        with(holder.binding) {
            nameTextView.text = room.name
            priceTextView.text = room.price.toString()
            pricePerTextView.text = room.pricePer
            initPeculiaritiesChipGroup(peculiaritiesChipGroup, room.peculiarities)
            initCarousel(carouselRecyclerView, room.imgUrls)
        }
    }
}