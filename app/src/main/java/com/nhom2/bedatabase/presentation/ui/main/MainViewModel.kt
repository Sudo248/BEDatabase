package com.nhom2.bedatabase.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nhom2.bedatabase.domain.common.Result
import com.nhom2.bedatabase.domain.models.Eng
import com.nhom2.bedatabase.domain.models.Group
import com.nhom2.bedatabase.domain.models.User
import com.nhom2.bedatabase.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: MainRepository
) : ViewModel() {

    private val _user: MutableLiveData<User> = MutableLiveData()
    val user: LiveData<User> = _user

    private val _vocabularies: MutableLiveData<List<Eng>> = MutableLiveData()
    val vocabularies: LiveData<List<Eng>> = _vocabularies

    private val _groups: MutableLiveData<Group> = MutableLiveData()
    val groups: LiveData<Group> = _groups

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _user.postValue(repo.getUserFromSharePref())
        }

    }

    private fun getDataForUser(user_id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getEngsByUserId(user_id).collect{ result ->
                if(result is Result.Success){
                    result.data.let{ list ->
                        _vocabularies.postValue(list)
                    }
                }
            }
        }
        viewModelScope.launch(Dispatchers.IO) {

        }
    }

}