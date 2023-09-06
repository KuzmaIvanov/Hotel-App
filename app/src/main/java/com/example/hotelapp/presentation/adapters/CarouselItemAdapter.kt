package com.example.hotelapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.hotelapp.databinding.ItemCarouselBinding

class CarouselItemAdapter(
    var hotelImgUrls: List<String>
) : RecyclerView.Adapter<CarouselItemAdapter.CarouselItemViewHolder>() {

    class CarouselItemViewHolder(
        val binding: ItemCarouselBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCarouselBinding.inflate(inflater, parent, false)
        return CarouselItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return hotelImgUrls.size
    }

    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {
        val hotelImgUrl = hotelImgUrls[position]
        holder.binding.carouselImageView.load(hotelImgUrl)
    }
}