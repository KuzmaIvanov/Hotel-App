package com.example.hotelapp.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.hotelapp.R
import com.example.hotelapp.databinding.FragmentSuccessBinding

class SuccessFragment : Fragment(R.layout.fragment_success) {

    private var _binding: FragmentSuccessBinding? = null
    private val binding get() = requireNotNull(_binding) { getString(R.string.binding_is_not_init) }
    private val args: SuccessFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initListeners() {
        binding.superButton.setOnClickListener {
            navigateToHotel()
        }
    }

    private fun initViews() {
        binding.orderConfirmationText.text = getConfirmationText()
    }

    private fun getConfirmationText(): String {
        val resultText = StringBuilder(getString(R.string.order_confirmation_text))
        val indexToAppend = resultText.indexOfFirst { it == '№' } + 1
        resultText.insert(indexToAppend, args.orderNumber)
        return resultText.toString()
    }

    private fun navigateToHotel() {
        val action = SuccessFragmentDirections.actionSuccessFragmentToHotelFragment()
        findNavController().navigate(action)
    }
}