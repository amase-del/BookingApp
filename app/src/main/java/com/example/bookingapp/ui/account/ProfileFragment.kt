package com.example.bookingapp.ui.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bookingapp.R
import com.example.bookingapp.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.logoutBtn.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }
        return binding.root
    }


    private fun userLoggedIn() {
        binding.userEmail.isVisible = true
        binding.logoutBtn.isVisible = true
    }

    override fun onStart() {
        super.onStart()
        userViewModel.getCurrentUser()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}