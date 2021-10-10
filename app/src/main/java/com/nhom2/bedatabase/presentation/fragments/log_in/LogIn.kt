package com.nhom2.bedatabase.presentation.fragments.log_in

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.nhom2.bedatabase.R
import com.nhom2.bedatabase.databinding.FragmentLogInBinding

class LogIn : Fragment() {
    lateinit var binding: FragmentLogInBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLogInBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkClickEvent(view)
    }

    private fun checkClickEvent(view: View) {
        val navController = Navigation.findNavController(view)
        binding.btnSwitchToSignUp.setOnClickListener {
            navController.navigate(R.id.action_logIn_to_signUp)
        }
        binding.btnLogIn.setOnClickListener {
            // Log in successfully
            navController.navigate(R.id.action_logIn_to_home2)
        }
    }
}