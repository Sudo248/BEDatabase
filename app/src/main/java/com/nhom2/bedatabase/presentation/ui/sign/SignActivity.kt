package com.nhom2.bedatabase.presentation.ui.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.nhom2.bedatabase.R
import com.nhom2.bedatabase.data.util.Utils
import com.nhom2.bedatabase.databinding.ActivitySignBinding
import com.nhom2.bedatabase.domain.common.Result
import com.nhom2.bedatabase.domain.repository.MainRepository
import com.nhom2.bedatabase.presentation.ui.main.LoadingScreen
import com.nhom2.bedatabase.presentation.ui.main.MainActivity
import com.nhom2.bedatabase.presentation.ui.sign.fragments.SignInFragment
import com.nhom2.bedatabase.presentation.ui.sign.fragments.SignUpFragment
import com.nhom2.bedatabase.presentation.ui.sign.view_model.SignViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SignActivity: AppCompatActivity() {

    lateinit var binding: ActivitySignBinding
    var isSignIn: Boolean = true
    private val viewModel: SignViewModel by viewModels()
    private val TAG = "Sign Activity"
    private val loadingScreen by lazy {LoadingScreen()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpUi()
        observer()
    }

    private fun setUpUi(){
        viewSignIn()
        with(binding){
            tvToSignUp.setOnClickListener {
                navigateFragment(1)
            }

            btnBackToSignIn.setOnClickListener {
                navigateFragment(0)
            }

            fabSignIn.setOnClickListener {
                if (isSignIn){
                    viewModel.signIn()
                } else {
                    viewModel.signUp()
                }
            }
        }
    }

    private fun observer(){

        viewModel.result.observe(this){
            when(it){
                is Result.Loading -> {
                    loadingScreen.show(supportFragmentManager, null)
                    binding.root.isFocusable = false
                }
                is Result.Error ->{
                    if(loadingScreen.isVisible){
                        loadingScreen.dismiss()
                        binding.root.isFocusable = true
                    }
                }
                else ->{
//                    navigate to Home
                    if(loadingScreen.isVisible)
                        loadingScreen.dismiss()
                    if (isSignIn){
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        //navigate back to SignIn
                        viewModel.passwordsIsEqual.value?.let{ equal ->
                            if (equal){
                                navigateFragment(0)
                                viewSignIn()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun viewSignIn(){
        binding.lnNewUser.visibility = View.VISIBLE
        binding.btnBackToSignIn.visibility = View.GONE
        binding.tvSign.text = getString(R.string.sign_in)
        isSignIn = true
    }

    private fun viewSignUp(){
        binding.lnNewUser.visibility = View.GONE
        binding.btnBackToSignIn.visibility = View.VISIBLE
        binding.tvSign.text = getString(R.string.sign_up)
        isSignIn = false
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