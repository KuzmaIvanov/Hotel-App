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
import com.example.hotelapp.R
import com.example.hotelapp.databinding.FragmentRoomsBinding
import com.example.hotelapp.presentation.adapters.CarouselItemAdapter
import com.example.hotelapp.presentation.adapters.RoomsItemAdapter
import com.example.hotelapp.presentation.viewmodels.RoomViewModel
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.FullScreenCarouselStrategy
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class RoomsFragment : Fragment(R.layout.fragment_rooms) {

    private var _binding: FragmentRoomsBinding? = null
    private val binding get() = requireNotNull(_binding) { getString(R.string.binding_is_not_init) }
    private val viewModel by activityViewModels<RoomViewModel>()
    private lateinit var roomsAdapter: RoomsItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoomsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeRoomsUiState()
        initRoomsRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeRoomsUiState() {
        viewModel.roomsUiState.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                if (result.isSuccess) {
                    val rooms = result.getOrThrow()
                    binding.roomsRecyclerView.visibility = View.VISIBLE
                    roomsAdapter.rooms = rooms
                    roomsAdapter.notifyDataSetChanged()
                } else {
                    showErrorToast()
                }
                binding.roomsProgressIndicator.visibility = View.GONE
            }
        }
    }

    private fun initRoomsRecyclerView() {
        roomsAdapter = RoomsItemAdapter(
            rooms = emptyList(),
            initPeculiaritiesChipGroup = { chipGroup, peculiarities ->
                addPeculiarityChips(chipGroup, peculiarities)
            },
            initCarousel = { recyclerView, imgUrls ->
                recyclerView.adapter = CarouselItemAdapter(imgUrls)
                recyclerView.layoutManager =
                    CarouselLayoutManager(FullScreenCarouselStrategy(), RecyclerView.HORIZONTAL)
                recyclerView.onFlingListener = null
                CarouselSnapHelper().attachToRecyclerView(recyclerView)
            },
            onSelectRoom = {
                navigateToBooking()
            }
        )
        binding.roomsRecyclerView.adapter = roomsAdapter
    }

    private fun addPeculiarityChips(chipGroup: ChipGroup, peculiarities: List<String>) {
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
            chipGroup.addView(peculiarityChip)
        }
    }

    private fun showErrorToast() {
        Toast.makeText(
            requireContext(),
            getString(R.string.get_rooms_error_message),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun navigateToBooking() {
        val action = RoomsFragmentDirections.actionRoomsFragmentToBookingFragment()
        findNavController().navigate(action)
    }
}