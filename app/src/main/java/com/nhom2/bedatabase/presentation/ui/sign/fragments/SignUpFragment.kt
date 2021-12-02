package com.nhom2.bedatabase.presentation.ui.sign.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import com.nhom2.bedatabase.R
import com.nhom2.bedatabase.databinding.FragmentSignUpBinding
import com.nhom2.bedatabase.domain.common.Result
import com.nhom2.bedatabase.presentation.ui.sign.view_model.SignViewModel


class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel by activityViewModels<SignViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            tiedtConfirmPassword.doOnTextChanged { text, _, _, _ ->
                if (!text.isNullOrBlank()){
                    viewModel.setConfirmPassword(text.toString())
                }
            }
        }
        viewModel.confirmPassword.observe(viewLifecycleOwner){
            viewModel.comparePassword()
        }
        viewModel.passwordsIsEqual.observe(viewLifecycleOwner){
            if (!it){
                binding.tilSignUpConfirmPassword.error = getString(R.string.passwords_mismatch)
            } else {
                binding.tilSignUpConfirmPassword.error = null
            }
        }
        viewModel.result.observe(viewLifecycleOwner){
            if (it is Result.Error){
                binding.tilSignUpConfirmPassword.error = it.message
            }
        }
    }
}