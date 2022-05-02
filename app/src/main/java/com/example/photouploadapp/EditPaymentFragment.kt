package com.example.photouploadapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.photouploadapp.databinding.FragmentEditPaymentBinding
import com.example.photouploadapp.viewmodel.SharedPaymentViewModel
import com.example.photouploadapp.component.MyApplication
import javax.inject.Inject

class EditPaymentFragment : Fragment() {

    private lateinit var binding: FragmentEditPaymentBinding

    @Inject
    lateinit var viewModelShared: SharedPaymentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEditPaymentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectDagger()
    }

    private fun injectDagger() {
        (requireActivity().application as MyApplication).appComponent
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.paymentTv.addTextChangedListener {
            binding.icSwitch.isChecked = it?.isNotEmpty() == true
        }

        binding.confirmPaymentButton.setOnClickListener {
            val payment = binding.paymentTv.text.toString()
            findNavController().previousBackStackEntry?.savedStateHandle?.set("key", payment)
            findNavController().popBackStack()
        }
    }
}
