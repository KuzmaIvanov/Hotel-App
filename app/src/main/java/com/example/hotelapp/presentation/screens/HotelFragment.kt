package com.example.hotelapp.presentation.screens

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Hotel
import com.example.hotelapp.R
import com.example.hotelapp.databinding.FragmentHotelBinding
import com.example.hotelapp.presentation.adapters.CarouselItemAdapter
import com.example.hotelapp.presentation.viewmodels.HotelViewModel
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.FullScreenCarouselStrategy
import com.google.android.material.chip.Chip

class HotelFragment : Fragment(R.layout.fragment_hotel) {

    private var _binding: FragmentHotelBinding? = null
    private val binding get() = requireNotNull(_binding) { getString(R.string.binding_is_not_init) }
    private val viewModel by activityViewModels<HotelViewModel>()
    private lateinit var adapter: CarouselItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHotelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initListeners()
        observeHotelUiState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeHotelUiState() {
        viewModel.hotelUiState.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                if (result.isSuccess) {
                    val hotel = result.getOrThrow()
                    initHotelDetailsViews(hotel)
                    binding.hotelGroup.visibility = View.VISIBLE
                } else {
                    showErrorToast()
                }
                binding.hotelProgressIndicator.visibility = View.GONE
            }
        }
    }

    private fun initRecyclerView() {
        adapter = CarouselItemAdapter(emptyList())
        binding.carouselRecyclerView.adapter = adapter
        binding.carouselRecyclerView.layoutManager =
            CarouselLayoutManager(FullScreenCarouselStrategy(), RecyclerView.HORIZONTAL)
        CarouselSnapHelper().attachToRecyclerView(binding.carouselRecyclerView)
    }

    private fun initHotelDetailsViews(hotel: Hotel) {
        with(binding) {
            ratingChip.text = hotel.rating
                .toString()
                .plus(" ")
                .plus(hotel.ratingName)
            nameTextView.text = hotel.name
            addressTextView.text = hotel.address
            priceTextView.text = hotel.minimalPrice.toString()
            priceForItTextView.text = hotel.priceForIt
            descTextView.text = hotel.description
            addPeculiarityChips(hotel.peculiarities)
        }
        adapter.hotelImgUrls = hotel.imgUrls
        adapter.notifyDataSetChanged()
    }

    private fun initListeners() {
        binding.chooseApartmentButton.setOnClickListener {
            navigateToRooms()
        }
    }

    private fun showErrorToast() {
        Toast.makeText(
            requireContext(),
            getString(R.string.get_hotel_error_message),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun addPeculiarityChips(peculiarities: List<String>) {
        peculiarities.forEach {
            val peculiarityChip = Chip(requireContext()).apply {
                setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_text))
                chipBackgroundColor = ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.light_gray_card)
                )
                text = it
                isCheckable = false
                isClickable = false
                text = it
                chipStrokeColor = ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.light_gray_card)
                )
            }
            binding.peculiaritiesChipGroup.addView(peculiarityChip)
        }
    }

    private fun navigateToRooms() {
        val action =
            HotelFragmentDirections.actionHotelFragmentToRoomsFragment(binding.nameTextView.text.toString())
        findNavController().navigate(action)
    }
}