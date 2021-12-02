package com.nhom2.bedatabase.presentation.ui.sign.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import com.nhom2.bedatabase.data.util.Utils
import com.nhom2.bedatabase.databinding.FragmentSignInBinding
import com.nhom2.bedatabase.domain.common.Result
import com.nhom2.bedatabase.presentation.ui.sign.view_model.SignViewModel


class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private val viewModel by activityViewModels<SignViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tryToSignInWithToken()
        with(binding){
            tiedtEmail.doOnTextChanged { text, _, _, _ ->
                if (!text.isNullOrBlank()){
                    viewModel.setEmail(text.toString())
                }
            }
            tiedtPassword.doOnTextChanged { text, _, _, _ ->
                if (!text.isNullOrBlank()){
                    viewModel.setPassword(text.toString())
                }
            }
        }
        viewModel.result.observe(viewLifecycleOwner){
            if (it is Result.Error){
                binding.tilSignInPassword.error = it.message
            }
        }
    }

    private fun tryToSignInWithToken(){
        viewModel.signInWithToken()
    }
}