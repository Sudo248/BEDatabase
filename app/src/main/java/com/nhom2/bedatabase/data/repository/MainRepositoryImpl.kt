package com.nhom2.bedatabase.data.repository

import android.util.Log
import com.nhom2.bedatabase.data.api.ApiService
import com.nhom2.bedatabase.data.models.AccountChangePassword
import com.nhom2.bedatabase.data.models.AccountRequest
import com.nhom2.bedatabase.data.models.AccountResponse
import com.nhom2.bedatabase.data.prefs.Pref
import com.nhom2.bedatabase.data.util.Utils
import com.nhom2.bedatabase.data.util.toAccountRequest
import com.nhom2.bedatabase.domain.common.Result
import com.nhom2.bedatabase.domain.models.*
import com.nhom2.bedatabase.domain.repository.MainRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainRepositoryImpl(
    private val api: ApiService,
    private val pref: Pref
) : MainRepository {
    private val TAG = "MainRepositoryImpl"
    override suspend fun signIn(account: Account): Flow<Result<Boolean>> = flow{
        emit(Result.Loading)
        lateinit var accountResponse: AccountResponse
        try {
            Log.d("TAG", "signIn: ${account.password}")
            accountResponse = api.signIn(account.toAccountRequest())
            Utils.access_token = accountResponse.token
            pref.saveToken(accountResponse.token)
            val user = api.getUser(accountResponse.account_id!!)
            pref.saveCurrentUser(user)
            emit(Result.Success(true))

        }catch (e: Exception){
            if (accountResponse.message != null) emit(Result.Error("${accountResponse.message}"))
            else emit(Result.Error("${e.message}"))
            Log.e("TAG", "signIn: ${e.message}")
        }
    }

    override suspend fun signUp(account: Account): Flow<Result<Boolean>> = flow{
        emit(Result.Loading)
        lateinit var accountResponse: AccountResponse
        try {
            accountResponse = api.signUp(account.toAccountRequest())
            emit(Result.Success(true))
        }catch (e: Exception){
            if (accountResponse.message != null) emit(Result.Error("${accountResponse.message}"))
            else emit(Result.Error("${e.message}"))
        }
    }

    override suspend fun changePassword(account_id: Int, oldPassword: String, newPassword: String): Flow<Result<Boolean>> = flow {
        emit(Result.Loading)
        lateinit var account: AccountChangePassword
        try {
            Log.e(TAG, "changePassword: $account_id, $newPassword" )
            account = api.changePassword(AccountChangePassword(account_id = account_id,oldPassword = oldPassword, newPassword = newPassword))
            emit(Result.Success(true))
        }catch (e: Exception){
            if (account.message!= null) emit(Result.Error(account.message.toString()))
            else emit(Result.Error("${e.message}"))
        }
    }

    override suspend fun signOut() {
        try {
            pref.saveToken(null)
            api.signOut()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override suspend fun signInWithToken(): Flow<Result<Boolean>> = flow{
        emit(Result.Loading)
        try {
            val token = pref.getToken()
            val userId = pref.getCurrentUserId()
            Log.d(TAG, "signInWithToken: $token \n$userId")
            if(token == null || userId == -1){
                emit(Result.Error("Token null or user id invalid"))
            }
            else{
                Utils.access_token = token
                val user = api.getUser(userId)
                pref.saveCurrentUser(user)
                emit(Result.Success(true))
            }
        }catch (e: Exception){
            emit(Result.Error("${e.message}"))
        }
    }

    override suspend fun getUser(): Flow<Result<User>> = flow{
        emit(Result.Loading)
        try {
            val userId = pref.getCurrentUserId()
            val user = api.getUser(userId)
            pref.saveCurrentUser(user)
            emit(Result.Success(user))
        }catch (e: Exception){
            emit(Result.Error("${e.message}"))
        }
    }

    override suspend fun getUserFromSharePref(): User? {
        return pref.getCurrentUser()
    }

    override suspend fun putUser(user: User): Flow<Result<Boolean>> = flow{
        emit(Result.Loading)
        try {
            api.putUser(user)
            emit(Result.Success(true))

        }catch (e: Exception){
            emit(Result.Error("${e.message}"))
        }
    }

    override suspend fun getEngsByUserId(user_id: Int): Flow<Result<List<Eng>>> = flow{
        emit(Result.Loading)
        try {
            val listEng = api.getEngsByUserId(user_id)
            emit(Result.Success(listEng))
        }catch (e: Exception){
            emit(Result.Error("${e.message}"))
            Log.e(TAG, "getEngsByUserId: ${e.message}")
        }
    }

    override suspend fun getEngsByGroupId(group_id: Int): Flow<Result<List<Eng>>> = flow{
        emit(Result.Loading)
        try {
            val listEng = api.getEngsByGroupId(group_id)
            emit(Result.Success(listEng))
        }catch (e: Exception){
            emit(Result.Error("${e.message}"))
        }
    }

    override suspend fun postEng(eng: Eng): Flow<Result<Boolean>> = flow{
        emit(Result.Loading)
        try {
            api.postEng(eng)
            emit(Result.Success(true))
        }catch (e: Exception){
            emit(Result.Error("${e.message}"))
        }
    }

    override suspend fun putEng(eng: Eng): Flow<Result<Boolean>> = flow{
        emit(Result.Loading)
        try {
            api.putEng(eng)
            emit(Result.Success(true))
        }catch (e: Exception){
            emit(Result.Error("${e.message}"))
        }
    }

    override suspend fun deleteEng(eng_id: Int): Flow<Result<Boolean>> = flow{
        emit(Result.Loading)
        try {
            api.deleteEngById(eng_id)
            emit(Result.Success(true))
        }catch (e: Exception){
            emit(Result.Error("${e.message}"))
        }
    }

    override suspend fun getGroups(): Flow<Result<List<Group>>> = flow{
        emit(Result.Loading)
        try {
            val listGroup = api.getGroups()
            emit(Result.Success(listGroup))
        }catch (e: Exception){
            emit(Result.Error("${e.message}"))
        }
    }

    override suspend fun postGroup(group: Group): Flow<Result<Boolean>> = flow{
        emit(Result.Loading)
        try {
            api.postGroup(group)
            emit(Result.Success(true))
        }catch (e: Exception){
            emit(Result.Error("${e.message}"))
        }
    }

    override suspend fun putGroup(group: Group): Flow<Result<Boolean>> = flow{
        emit(Result.Loading)
        try {
            api.putGroup(group)
            emit(Result.Success(true))
        }catch (e: Exception){
            emit(Result.Error("${e.message}"))
        }
    }
    override suspend fun deleteGroup(group_id: Int): Flow<Result<Boolean>> = flow{
        emit(Result.Loading)
        try {
            api.deleteGroupById(group_id)
            emit(Result.Success(true))
        }catch (e: Exception){
            emit(Result.Error("${e.message}"))
        }
    }

    override suspend fun postVnByEngId(vn: Vn): Flow<Result<Boolean>> = flow{
        emit(Result.Loading)
        try {
            api.postVn(vn)
            emit(Result.Success(true))
        }catch (e: Exception){
            emit(Result.Error("${e.message}"))
        }
    }

    override suspend fun putVnByEngId(vn: Vn): Flow<Result<Boolean>> = flow{
        emit(Result.Loading)
        try {
            api.putVn(vn)
            emit(Result.Success(true))
        }catch (e: Exception){
            emit(Result.Error("${e.message}"))
        }
    }

    override suspend fun deleteVn(vn_id: Int): Flow<Result<Boolean>> = flow{
        emit(Result.Loading)
        try {
            api.deleteVn(vn_id)
            emit(Result.Success(true))
        }catch (e: Exception){
            emit(Result.Error("${e.message}"))
        }
    }
}