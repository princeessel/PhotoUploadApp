package com.example.photouploadapp

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.photouploadapp.component.MyApplication
import com.example.photouploadapp.databinding.FragmentProfileBinding

import com.example.photouploadapp.utils.PrefManager
import com.example.photouploadapp.viewmodel.ProfileViewModel
import com.example.photouploadapp.viewmodel.SharedPaymentViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

import javax.inject.Inject

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    @Inject
    lateinit var viewModel: ProfileViewModel

    @Inject
    lateinit var sharedPaymentViewModel: SharedPaymentViewModel

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val navController = findNavController()
        val navView: BottomNavigationView = binding.bottomNavigation
        navView.setupWithNavController(navController)

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

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        //TODO 3. Get userId from Shared Preference and use it to get user data from room db
        val userId = PrefManager.retrieveUserId(requireActivity())
        getLoggedInUserData(userId)

        binding.logout.setOnClickListener {
            logout()
        }

        initObservers()
        initLoanBalance(userId)
        initTimeOfDay()

        binding.payButton.setOnClickListener {
            Toast.makeText(context, "Pay Button clicked", Toast.LENGTH_LONG).show()
            navController.navigate(R.id.action_profileFragment_to_paymentFragment)
        }

        binding.viewButton.setOnClickListener {
            Toast.makeText(context, "View Button clicked", Toast.LENGTH_LONG).show()
        }


    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLoggedInUserData(id: Long) {
        viewModel.getUserData(id = id)
    }

    private fun initTimeOfDay() {
        viewModel.getTimeOfDay()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initLoanBalance(userId: Long) {
        viewModel.getLoanBalance(userId)
    }

    //TODO 4. To logout delete userId from Shared Preference
    private fun logout() {
        PrefManager.clearUserId(requireActivity())
        navController.navigate(R.id.action_profileFragment_to_mainFragment)
    }

    private fun initObservers() {
        viewModel.loggedInUser.observe(viewLifecycleOwner, {
            with(binding) {
                fullname.text = it.fullName
            }
        })

        viewModel.getTimeOfDay.observe(viewLifecycleOwner, {
            with(binding.userGreetingTv) {
                when (it) {
                    in 0..11 -> {
                        text = context.getString(R.string.good_morning)
                    }
                    in 13..15 -> {
                        text = context.getString(R.string.good_afternoon)
                    }
                    in 17..20 -> {
                        text = context.getString(R.string.good_evening)
                    }
                    in 22..23 -> {
                        text = context.getString(R.string.good_night)
                    }
                }
            }
        })

        viewModel.loanBalance.observe(viewLifecycleOwner, {
            binding.loanBalance.text = it
        })

        viewModel.paymentError.observe(viewLifecycleOwner, { error ->
            Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
        })

        viewModel.error.observe(viewLifecycleOwner, { error ->
            Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
        })
    }

}
