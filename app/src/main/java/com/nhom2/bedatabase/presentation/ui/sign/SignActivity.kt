package com.nhom2.bedatabase.presentation.ui.sign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.nhom2.bedatabase.R
import com.nhom2.bedatabase.databinding.ActivitySignBinding
import com.nhom2.bedatabase.presentation.ui.sign.fragments.SignInFragment
import com.nhom2.bedatabase.presentation.ui.sign.fragments.SignUpFragment
import com.nhom2.bedatabase.presentation.ui.sign.view_model.SignViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignBinding

    private val viewModel: SignViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpUi()
        observer()
    }

    private fun setUpUi(){

        with(binding){
            tvToSignUp.setOnClickListener {
                navigateFragment(1)
            }

            btnBackToSignIn.setOnClickListener {
                navigateFragment(0)
            }

        }
    }

    private fun observer(){

    }

    private fun viewSignIn(){
        binding.lnNewUser.visibility = View.VISIBLE
        binding.btnBackToSignIn.visibility = View.GONE
        binding.tvSign.text = getString(R.string.sign_in)
    }

    private fun viewSignUp(){
        binding.lnNewUser.visibility = View.GONE
        binding.btnBackToSignIn.visibility = View.VISIBLE
        binding.tvSign.text = getString(R.string.sign_up)
    }

    private fun navigateFragment(position: Int = 0){
        when(position){
            0 -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fcv_sign, SignInFragment())
                    .commit()

                viewSignIn()
            }
            1 -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fcv_sign, SignUpFragment())
                    .commit()

                viewSignUp()
            }
            else -> Unit
        }
    }

}