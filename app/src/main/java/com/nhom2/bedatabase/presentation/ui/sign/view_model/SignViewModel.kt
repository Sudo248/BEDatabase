package com.nhom2.bedatabase.presentation.ui.sign.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nhom2.bedatabase.data.util.Utils
import com.nhom2.bedatabase.domain.common.Result
import com.nhom2.bedatabase.domain.models.Account
import com.nhom2.bedatabase.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel(){
    private val TAG = "Sign viewModel"
    private var _email: MutableLiveData<String> =  MutableLiveData()
    val email: LiveData<String> = _email

    private var _password: MutableLiveData<String> =  MutableLiveData()
    val password: LiveData<String> = _password

    private var _confirmPassword: MutableLiveData<String> =  MutableLiveData()
    val confirmPassword: LiveData<String> = _confirmPassword

    private var _passwordsIsEqual: MutableLiveData<Boolean> = MutableLiveData(true)
    val passwordsIsEqual: LiveData<Boolean> = _passwordsIsEqual

    private var _result: MutableLiveData<Result<Boolean>> = MutableLiveData()
    val result: LiveData<Result<Boolean>> = _result

    fun setEmail(email: String){
        Log.d(TAG, "setEmail: $email", )
        _email.postValue(email)
    }

    fun setPassword(password: String){
        Log.d(TAG, "setPassword: $password", )
        _password.postValue(password)
    }

    fun setConfirmPassword(confirmPassword: String){
        Log.e(TAG, "comparePassword: ${this.confirmPassword.value}, ${password.value}", )
        _confirmPassword.postValue(confirmPassword)
    }

    fun comparePassword(){
        _passwordsIsEqual.postValue(confirmPassword.value == password.value)
    }

    fun signIn(){
        Log.e(TAG, "signIn:${password.value}, ${email.value}")
        viewModelScope.launch(Dispatchers.IO){
            mainRepository.signIn(Account(
                email = email.value,
                password = Utils.hash(password.value.toString())
            )).collect {
                when(it){
                    is Result.Loading -> {
                        _result.postValue(it)
                    }
                    is Result.Success -> {
                        _result.postValue(Result.Success(true))
                    }
                    is Result.Error ->{
                        _result.postValue(it)
                    }
                }
            }
        }
    }
    fun signUp(){
        Log.d(TAG, "signUp: ${password.value} {${email.value}")
        viewModelScope.launch(Dispatchers.IO){
            mainRepository.signUp(Account(
                email = email.value,
                password = Utils.hash(password.value.toString())
            )).collect{
                when(it){
                    is Result.Loading ->{
                        _result.postValue(it)
                    }
                    is Result.Success -> {
                        _result.postValue(Result.Success(true))
                    }
                    is Result.Error ->{
                        _result.postValue(it)
                    }
                }
            }
        }
    }
    fun signInWithToken(){
        Log.d(TAG, "signInWithToken")
        viewModelScope.launch(Dispatchers.IO){
            mainRepository.signInWithToken().collect{
                when(it){
                    is Result.Loading -> {
                        _result.postValue(it)
                    }
                    is Result.Success -> {
                        _result.postValue(Result.Success(true))
                    }
                    is Result.Error ->{
                        _result.postValue(it)
                    }
                }
            }
        }
    }
}