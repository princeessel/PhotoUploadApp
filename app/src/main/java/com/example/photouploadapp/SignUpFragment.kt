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
import com.example.photouploadapp.component.MyApplication
import com.example.photouploadapp.databinding.FragmentSignUpBinding
import com.example.photouploadapp.viewmodel.SignUpViewModel
import javax.inject.Inject


class SignUpFragment : Fragment() {
    private lateinit var signUpBinding: FragmentSignUpBinding

    @Inject lateinit var viewModel: SignUpViewModel

    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        signUpBinding = FragmentSignUpBinding.inflate(inflater, container, false)

        return signUpBinding.root
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
        signUpBinding.completeSignUp.setOnClickListener {
            onSignUp()
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.error.observe(viewLifecycleOwner, { error ->
            Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
        })
    }

    private fun onSignUp() {
        with(signUpBinding) {
            val username = enterUsername.text.toString().trim()
            val email = enterEmail.text.toString().trim()
            val password = enterPassword.text.toString().trim()
            val fullName = fullname.text.toString().trim()
            val loanAmount = enterLoanAmount.text.toString().trim().toLong()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || fullName.isEmpty()) {
                Toast.makeText(context, "Please all fields are required", Toast.LENGTH_LONG).show()
            } else {
                viewModel.signUp(username, email, password, fullName, loanAmount)
                navController.navigate(R.id.action_signUpFragment_to_mainFragment)
                Toast.makeText(context, "Account successfully created", Toast.LENGTH_LONG).show()
            }
        }
    }
}
