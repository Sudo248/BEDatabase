package com.nhom2.bedatabase.presentation.ui.main

import android.util.Log
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

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _user: MutableLiveData<User> = MutableLiveData()
    val user: LiveData<User> = _user

    private val _vocabularies: MutableLiveData<List<Eng>> = MutableLiveData()
    val vocabularies: LiveData<List<Eng>> = _vocabularies

    private val _groups: MutableLiveData<List<Group>> = MutableLiveData()
    val groups: LiveData<List<Group>> = _groups

//    private val _currentVocabulary = MutableLiveData<Eng>()
//    val currentVocabulary: LiveData<Eng> = _currentVocabulary

//    private val _currentGroup = MutableLiveData<Group>()
//    val currentGroup: LiveData<Group> = _currentGroup

    var currentVocabulary: Eng? = null
    var currentGroup: Group? = null

    private var process = 0

    private val TAG = "Main ViewModel"

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val user = repo.getUserFromSharePref()
            user?.let{
                _user.postValue(it)
                getDataForUser(it.user_id)
                Log.d(TAG, "Current user_id: ${it.user_id}")
            }
        }
    }

    private fun getDataForUser(user_id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getEngsByUserId(user_id).collect{ result ->
                when(result){
                    is Result.Loading -> {
                        _isLoading.postValue(true)
                    }
                    is Result.Success -> {
                        result.data.let{ list ->
                            Log.d(TAG, "getDataForUser: ${list.size}")
                            _vocabularies.postValue(list)
                        }
                        process += 1
                        if(process == 2)
                            _isLoading.postValue(false)
                    }
                    is Result.Error -> {
                        _isLoading.postValue(false)
                    }
                }
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            repo.getGroups().collect { result ->
                when(result){
                    is Result.Loading -> {
                        _isLoading.postValue(true)
                    }
                    is Result.Success -> {
                        result.data.let{ list ->
                            _groups.postValue(list)
                        }
                        process += 1
                        if(process == 2)
                            _isLoading.postValue(false)
                    }
                    is Result.Error -> {
                        _isLoading.postValue(false)
                    }
                }
            }
        }
    }

    fun setCurrentVocabulary(pos: Int){
        _vocabularies.value?.let {
            currentVocabulary = it.get(pos)
        }
    }

    fun setCurrentGroup(pos: Int){
        _groups.value?.let {
            currentGroup = it.get(pos)
        }
    }

    fun deleteVocabulary(pos: Int){
        val list = _vocabularies.value as MutableList
        list.removeAt(pos)
        _vocabularies.postValue(list)
    }

    fun deleteGroup(pos: Int){
        val list = _groups.value as MutableList
        list.removeAt(pos)
        _groups.postValue(list)
    }

    fun getGroupNameById(group_id: Int): String{
        var nameGroup = "Unknown"
       _groups.value?.let{ list ->
           val group = list.first { it.group_id == group_id }
           nameGroup = group.name
       }
        return nameGroup
    }

}