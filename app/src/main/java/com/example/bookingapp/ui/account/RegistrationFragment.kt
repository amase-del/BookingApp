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
import com.example.bookingapp.databinding.FragmentRegistrationBinding
import com.example.bookingapp.utils.ResultMessage
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        subscribeToRegistrationEvents()

        binding.btnCreateAccount.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etMail.text.toString()
            val password = binding.etPassword.text.toString()

            userViewModel.createUser(
                name.trim(),
                email.trim(),
                password.trim()
            )
        }

        return binding.root
    }

    private fun subscribeToRegistrationEvents() = lifecycleScope.launch {
        userViewModel.registerState.collect { result ->
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
        binding.createUserProgressBar.isVisible = true
    }

    private fun hideProgressBar() {
        binding.createUserProgressBar.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}