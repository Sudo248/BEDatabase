package com.nhom2.bedatabase.presentation.ui.main.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nhom2.bedatabase.domain.models.User
import com.nhom2.bedatabase.domain.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.nhom2.bedatabase.domain.common.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val mainRepository: MainRepository
): ViewModel() {
    private var _user: MutableLiveData<User> = MutableLiveData()
    val user: LiveData<User> = _user

    private var _result: MutableLiveData<Result<Nothing>> = MutableLiveData()
    val result: LiveData<Result<Nothing>> = _result

    init{
        getUser()
    }

    private fun getUser(){
        viewModelScope.launch(Dispatchers.IO){
            mainRepository.getUser().collect {
                when(it){
                    is Result.Loading ->{
                        _result.postValue(it)
                    }
                    is Result.Success -> {

                    }
                    is Result.Error -> {
                        _result.postValue(it)
                    }
                }
            }
        }
    }
}