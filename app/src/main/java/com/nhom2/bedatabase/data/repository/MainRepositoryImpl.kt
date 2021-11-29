package com.nhom2.bedatabase.data.repository

import com.nhom2.bedatabase.data.api.ApiService
import com.nhom2.bedatabase.data.prefs.Pref
import com.nhom2.bedatabase.data.util.Utils
import com.nhom2.bedatabase.data.util.toAccount
import com.nhom2.bedatabase.data.util.toAccountRequest
import com.nhom2.bedatabase.domain.common.Result
import com.nhom2.bedatabase.domain.models.*
import com.nhom2.bedatabase.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainRepositoryImpl(
    private val api: ApiService,
    private val pref: Pref
) : MainRepository {
    override suspend fun signIn(account: Account): Flow<Result<Account>> = flow{
        emit(Result.Loading)
        try {
            val accountResponse = api.signIn(account.toAccountRequest())
            if (accountResponse.account_id == null){
                emit(Result.Error(accountResponse.message))
            }else{
                Utils.access_token = accountResponse.token
                pref.saveToken(accountResponse.token)
                emit(Result.Success(accountResponse.toAccount()))
            }
        }catch (e: Exception){
            emit(Result.Error("${e.message}"))
        }
    }

    override suspend fun signUp(account: Account): Flow<Result<Boolean>> = flow{
        emit(Result.Loading)
        try {
            val accountResponse = api.signIn(account.toAccountRequest())
            if (accountResponse.account_id == null){
                emit(Result.Error(accountResponse.message))
            }else{
                emit(Result.Success(true))
            }
        }catch (e: Exception){
            emit(Result.Error("${e.message}"))
        }
    }

    override suspend fun signOut() {
        try {
            api.signOut()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override suspend fun signInWithToken(): Flow<Result<User?>> = flow{
        emit(Result.Loading)
        try {

        }catch (e: Exception){
            emit(Result.Error("${e.message}"))
        }
    }

    override suspend fun getUser(): Flow<Result<User>> {
        TODO("Not yet implemented")
    }

    override suspend fun putUser(): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun getEngsByUserId(user_id: Int): Flow<Result<Eng>> {
        TODO("Not yet implemented")
    }

    override suspend fun getEngsByGroupId(group_id: Int): Flow<Result<Eng>> {
        TODO("Not yet implemented")
    }

    override suspend fun postEng(eng: Eng): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun putEng(eng: Eng): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteEng(eng_id: Int): Flow<Result<Nothing>> {
        TODO("Not yet implemented")
    }

    override suspend fun getGroups(): Flow<Result<Group>> {
        TODO("Not yet implemented")
    }

    override suspend fun postGroup(group: Group): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun putGroup(group: Group): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteGroup(group_id: Int): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun postVnByEngId(vn: Vn): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun putVnByEngId(vn: Vn): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteVn(vn_id: Int): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }
}