package com.example.bookingapp.ui.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bookingapp.R
import com.example.bookingapp.data.remote.models.User
import com.example.bookingapp.databinding.FragmentLoginBinding
import com.example.bookingapp.utils.ResultMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        subscribeToLoginEvents()

        binding.btnLogin.setOnClickListener {
            val email = binding.etMail.text.toString()
            val password = binding.etPassword.text.toString()

            userViewModel.loginUser(
                "",
                email.trim(),
                password.trim()
            )
        }

        return binding.root
    }

    private fun subscribeToLoginEvents() = lifecycleScope.launch {
        userViewModel.loginState.collect { result ->
            when (result) {
                is ResultMessage.Success -> {
                    hideProgressBar()
                    Toast.makeText(requireContext(), "Account created", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
                is ResultMessage.Error -> {
                    hideProgressBar()
                    Toast.makeText(requireContext(), result.errorMessage, Toast.LENGTH_SHORT).show()
                }
                is ResultMessage.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun showProgressBar() {
        binding.loginProgressBar.isVisible = true
    }

    private fun hideProgressBar() {
        binding.loginProgressBar.isVisible = false
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}