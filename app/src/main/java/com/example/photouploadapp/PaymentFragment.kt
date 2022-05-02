package com.example.photouploadapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.photouploadapp.component.MyApplication
import com.example.photouploadapp.databinding.PaymentFragmentBinding
import com.example.photouploadapp.utils.PrefManager
import com.example.photouploadapp.viewmodel.ProfileViewModel
import com.example.photouploadapp.viewmodel.SharedPaymentViewModel

import javax.inject.Inject

class PaymentFragment : Fragment() {

    private lateinit var binding: PaymentFragmentBinding

    @Inject
    lateinit var viewModelShared: SharedPaymentViewModel

    @Inject
    lateinit var viewModel: ProfileViewModel

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PaymentFragmentBinding.inflate(inflater, container, false)
        initObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        if (binding.paymentAmount.text == "" || binding.paymentAmount.text.isBlank()) {
            binding.submitPaymentBtnContainer.setBackgroundResource(R.drawable.submit_payment_btn_disabled)
            binding.submitPaymentBtnContainer.isEnabled = false
        }

        binding.paymentAmountContainer.setOnClickListener {
            navController.navigate(R.id.action_paymentFragment_to_editPaymentFragment)

            Toast.makeText(context, "Payment submitted", Toast.LENGTH_LONG).show()
        }

        binding.submitPaymentBtnContainer.setOnClickListener {
            navigateUp()
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectDagger()
    }

    private fun injectDagger() {
        (requireActivity().application as MyApplication).appComponent
            .inject(this)
    }

    private fun initObservers() {

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("key")
            ?.observe(
                viewLifecycleOwner
            ) { result ->
                binding.paymentAmount.text = result

                if (binding.paymentAmount.text.toString().isNotBlank()) {
                    binding.submitPaymentBtnContainer.setBackgroundResource(R.drawable.submit_payment_btn)
                    binding.submitPaymentBtnContainer.isEnabled = true
                    binding.submitPaymentBtnContainer.isClickable = true
                }
            }
    }

    private fun navigateUp() {
        val userId = PrefManager.retrieveUserId(requireActivity())
        val payment = binding.paymentAmount.text.toString().toLong()
        val paidFrom = binding.paymentAccount.text.toString()

        viewModelShared.createPayment(userId, payment, "2022-04-22", paidFrom)

        findNavController().navigateUp()
    }
}
