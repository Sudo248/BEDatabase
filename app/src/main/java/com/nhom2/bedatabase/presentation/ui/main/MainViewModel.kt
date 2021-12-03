package com.nhom2.bedatabase.presentation.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nhom2.bedatabase.data.util.Utils
import com.nhom2.bedatabase.domain.common.Result
import com.nhom2.bedatabase.domain.models.Eng
import com.nhom2.bedatabase.domain.models.Group
import com.nhom2.bedatabase.domain.models.User
import com.nhom2.bedatabase.domain.models.Vn
import com.nhom2.bedatabase.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: MainRepository
) : ViewModel() {

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _result: MutableLiveData<Result<Boolean>> = MutableLiveData()
    val result: LiveData<Result<Boolean>> = _result

    private val _user: MutableLiveData<User> = MutableLiveData()
    val user: LiveData<User> = _user

    private val _vocabularies: MutableLiveData<List<Eng>> = MutableLiveData()
    val vocabularies: LiveData<List<Eng>> = _vocabularies

    private val _groups: MutableLiveData<List<Group>> = MutableLiveData()
    val groups: LiveData<List<Group>> = _groups

    private val _currentVocabulary = MutableLiveData<Eng>()
    val currentVocabulary: LiveData<Eng> = _currentVocabulary

    private var _currentPosEng = 0

//    private val _currentGroup = MutableLiveData<Group>()
//    val currentGroup: LiveData<Group> = _currentGroup

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

    fun updateUser(userName: String, pathImg: String? = null){
        val user = _user.value!!
        user.user_name = userName
        user.path_image = pathImg
        _user.postValue(user)
        viewModelScope.launch(Dispatchers.IO) {
            repo.putUser(user).collect {
                when(it) {
                    is Result.Loading -> {
                        _isLoading.postValue(true)
                    } else -> _isLoading.postValue(false)
                }
            }
        }
    }

    fun signOut(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.signOut()
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
            _currentVocabulary.postValue(it.get(pos))
            _currentPosEng = pos
        }
    }

    fun setGroupForCurrentVocabulary(group_id: Int){
        val vocabulary = _currentVocabulary.value?.copy(group_id = group_id)
        vocabulary?.let {voc -> _currentVocabulary.postValue(voc)}

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

    fun changePassword(oldPassword: String, newPassword: String){
        user.value?.let{
            viewModelScope.launch(Dispatchers.IO){
                repo.changePassword(it.user_id, Utils.hash(oldPassword), Utils.hash(newPassword)).collect {
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
                Log.e(TAG, "changePassword: done")
            }
        }
    }
    fun updateVocabulary(content: String, vn: String, pronunciation: String, pathImg: String?, type: String){
        Log.e(TAG, type, )
        viewModelScope.launch(Dispatchers.IO){
            _currentVocabulary.value?.let {
                val newEng = Eng(it.eng_id, it.group_id, pronunciation, content,type, pathImg, listOf(it.vns[0].copy(content = vn)))
                _vocabularies.value?.let{ list ->
                    list.get(_currentPosEng).group_id = newEng.group_id
                    list.get(_currentPosEng).content = newEng.content
                    list.get(_currentPosEng).path_image = newEng.path_image
                    list.get(_currentPosEng).pronunciation = newEng.pronunciation
                    list.get(_currentPosEng).type = newEng.type
                    list.get(_currentPosEng).vns = newEng.vns
                    _vocabularies.postValue(list)
                }
                repo.putEng(newEng).collect { res ->
                    if(res is Result.Loading){
                        _isLoading.postValue(true)
                    } else {
                        _isLoading.postValue(false)
                    }
                }
            }
        }
    }
    fun addGroup(group: Group){
        viewModelScope.launch(Dispatchers.IO){
            repo.postGroup(group).collect {
                if (it is Result.Loading){
                    _isLoading.postValue(true)
                } else {
                    _groups.value?.let{ list ->
                        val listGroup = list.toMutableList()
                        listGroup.add(group)
                        _groups.postValue(listGroup)
                        _user.value?.user_id?.let { it1 -> getDataForUser(it1) }
                    }
                    _isLoading.postValue(false)
                }
            }
        }
    }
}