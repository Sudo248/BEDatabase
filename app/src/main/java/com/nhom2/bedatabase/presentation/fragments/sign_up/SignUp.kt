package com.nhom2.bedatabase.presentation.fragments.sign_up

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.nhom2.bedatabase.R
import com.nhom2.bedatabase.databinding.FragmentSignUpBinding

class SignUp : Fragment() {
    lateinit var binding: FragmentSignUpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(
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
        binding.btnSignUp.setOnClickListener {
            //Sign up successfully
            navController.navigate(R.id.action_signUp_to_home2)
        }
    }
}