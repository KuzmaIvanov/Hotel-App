package com.example.hotelapp.presentation.screens

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.example.domain.model.Booking
import com.example.hotelapp.R
import com.example.hotelapp.databinding.FragmentBookingBinding
import com.example.hotelapp.presentation.viewmodels.BookingViewModel
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputLayout
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import kotlin.math.abs
import kotlin.random.Random

class BookingFragment : Fragment(R.layout.fragment_booking) {

    private var _binding: FragmentBookingBinding? = null
    private val binding get() = requireNotNull(_binding) { getString(R.string.binding_is_not_init) }
    private val viewModel by activityViewModels<BookingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMaskToPhoneNumberEditText()
        initListeners()
        observeBookingUiState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeBookingUiState() {
        viewModel.bookingUiState.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                if (result.isSuccess) {
                    val booking = result.getOrThrow()
                    initViews(booking)
                    binding.bookingGroup.visibility = View.VISIBLE
                } else {
                    showErrorToast()
                }
                binding.bookingProgressIndicator.visibility = View.GONE
            }
        }
    }

    private fun setMaskToPhoneNumberEditText() {
        val mask = MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER)
        val watcher = MaskFormatWatcher(mask)
        watcher.installOn(binding.phoneNumberTextInputLayout.editText!!)
    }

    private fun initListeners() {
        binding.addTouristCardView.setOnClickListener {
            addTouristView()
        }
        binding.payButton.setOnClickListener {
            if (isEverythingFilledIn()) {
                navigateToSuccess()
            }
        }
        binding.emailTextInputLayout.editText?.doAfterTextChanged {
            binding.emailTextInputLayout.error = null
        }
        binding.phoneNumberTextInputLayout.editText?.doAfterTextChanged {
            binding.phoneNumberTextInputLayout.error = null
        }
    }

    private fun initViews(booking: Booking) {
        addTouristView()
        with(binding) {
            ratingChip.text = booking.hotelRating
                .toString()
                .plus(' ')
                .plus(booking.ratingName)
            nameTextView.text = booking.hotelName
            addressTextView.text = booking.hotelAddress
            departureTextView.text = booking.departure
            arrivalCountryTextView.text = booking.arrivalCountry
            tourDatesTextView.text = booking.tourDateStart
                .plus('-')
                .plus(booking.tourDateStop)
            numberOfNightsTextView.text = booking.numberOfNights.toString()
            nameHotelTextView.text = booking.hotelName
            roomTextView.text = booking.room
            nutritionDataTextView.text = booking.nutrition
            val totalPrice = booking.tourPrice + booking.fuelCharge + booking.serviceCharge
            payButton.text = getString(R.string.pay_text)
                .plus(' ')
                .plus(totalPrice.toString())
                .plus(' ')
                .plus(getString(R.string.currency_rub_text_view))
        }
    }

    private fun addTouristView() {
        val touristView =
            layoutInflater.inflate(R.layout.item_tourist, binding.touristsLayout, false)
        val cardView = touristView.findViewById<MaterialCardView>(R.id.tourist_card_view)
        val hiddenView = touristView.findViewById<LinearLayout>(R.id.hidden_view)
        val arrow = touristView.findViewById<ImageView>(R.id.expand_image_view)
        val touristNumberTextView =
            touristView.findViewById<TextView>(R.id.number_tourist_text_view)
        val touristNumber = binding.touristsLayout.childCount + 1
        touristNumberTextView.text = touristNumber.toString()
        touristView.setOnClickListener {
            if (hiddenView.visibility == View.VISIBLE) {
                expandMoreCardView(cardView, hiddenView, arrow)
            } else {
                expandLessCardView(cardView, hiddenView, arrow)
            }
        }
        binding.touristsLayout.addView(touristView)
    }

    private fun expandMoreCardView(
        cardView: MaterialCardView,
        hiddenView: ViewGroup,
        clickItem: ImageView
    ) {
        TransitionManager.beginDelayedTransition(cardView, AutoTransition())
        hiddenView.visibility = View.GONE
        clickItem.setImageResource(R.drawable.ic_expand_more)
    }

    private fun expandLessCardView(
        cardView: MaterialCardView,
        hiddenView: ViewGroup,
        clickItem: ImageView
    ) {
        TransitionManager.beginDelayedTransition(cardView, AutoTransition())
        hiddenView.visibility = View.VISIBLE
        clickItem.setImageResource(R.drawable.ic_expand_less)
    }

    private fun showErrorToast() {
        Toast.makeText(
            requireContext(),
            getString(R.string.get_booking_error_message),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun isEverythingFilledIn(): Boolean {
        var isEverythingFilledIn = true
        if (!isValidPhoneNumber(binding.phoneNumberTextInputLayout.editText?.text.toString())) {
            binding.phoneNumberTextInputLayout.error = getString(R.string.enter_correct_data)
            isEverythingFilledIn = false
        }
        if (!isValidEmail(binding.emailTextInputLayout.editText?.text.toString())) {
            binding.emailTextInputLayout.error = getString(R.string.enter_correct_data)
            isEverythingFilledIn = false
        }
        if (!isCorrectTouristsInformation()) {
            isEverythingFilledIn = false
        }
        return isEverythingFilledIn
    }

    private fun isValidEmail(string: String): Boolean {
        return string.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(string).matches()
    }

    private fun isValidPhoneNumber(string: String): Boolean {
        return string.isNotEmpty() && string.length == SIZE_OF_RUS_PHONE_NUMBER_WITH_MASK
    }

    private fun isCorrectTouristsInformation(): Boolean {
        val touristsLayout = binding.touristsLayout
        var isCorrect = true
        for (i in 0..<touristsLayout.childCount) {
            val touristView = touristsLayout[i]
            val hiddenView = touristView.findViewById<LinearLayout>(R.id.hidden_view)
            for (j in 0..<hiddenView.childCount) {
                val textInputLayout = (hiddenView[j] as TextInputLayout)
                val editText = textInputLayout.editText
                editText?.doAfterTextChanged {
                    textInputLayout.error = null
                }
                if (editText?.text.isNullOrEmpty()) {
                    isCorrect = false
                    textInputLayout.error = getString(R.string.enter_correct_data)
                }
            }
        }
        return isCorrect
    }

    private fun navigateToSuccess() {
        val action =
            BookingFragmentDirections.actionBookingFragmentToSuccessFragment(abs(Random.nextInt()))
        findNavController().navigate(action)
    }

    companion object {
        private const val SIZE_OF_RUS_PHONE_NUMBER_WITH_MASK = 18
    }
}