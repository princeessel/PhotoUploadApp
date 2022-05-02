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
import com.example.photouploadapp.databinding.FragmentMainBinding

import com.example.photouploadapp.utils.PrefManager
import com.example.photouploadapp.viewmodel.LoginViewModel
import javax.inject.Inject


class MainFragment : Fragment() {
    private lateinit var bindingMain: FragmentMainBinding

    @Inject
    lateinit var viewModel: LoginViewModel

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        bindingMain = FragmentMainBinding.inflate(inflater, container, false)

        return bindingMain.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectDagger()
    }

    private fun injectDagger() {
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        //TODO 1. check if shared preference contains userId -> If true move to profile screen
        if (PrefManager.isUserLoggedIn(requireActivity())) {
            navController.navigate(R.id.action_mainFragment_to_profileFragment)
        }

        navigateToSignupScreen()

        bindingMain.loginBtn.setOnClickListener {
            login()
            initObservers()
        }
    }


    private fun initObservers() {
        viewModel.loginUser.observe(viewLifecycleOwner, { user ->
            val userId = user.userId
            //TODO 2. save userId to shared Preference
            PrefManager.saveUserId(requireActivity(), userId)
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToProfileFragment())
        })

        viewModel.error.observe(viewLifecycleOwner, { error ->
            Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
        })
    }

    private fun login() {
        val username = bindingMain.enterUsername.text.toString().trim()
        val password = bindingMain.enterPassword.text.toString().trim()

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, "Please enter username and password", Toast.LENGTH_LONG).show()
        } else {
            viewModel.login(username, password)
        }
    }

    private fun navigateToSignupScreen() {
        bindingMain.signUpBtn.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_signUpFragment)
        }
    }
}
