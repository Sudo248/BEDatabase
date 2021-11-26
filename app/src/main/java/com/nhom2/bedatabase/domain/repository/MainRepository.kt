package com.nhom2.bedatabase.domain.repository

import com.nhom2.bedatabase.domain.common.Result
import com.nhom2.bedatabase.domain.models.*
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    suspend fun signIn(account: Account): Flow<Result<Account>>

    suspend fun signUp(account: Account): Flow<Result<Boolean>>

    suspend fun signOut()

    suspend fun signInWithToken(): Flow<Result<User?>>

    suspend fun getUser(): Flow<Result<User>>

    suspend fun putUser(): Flow<Result<Boolean>>

    suspend fun getEngsByUserId(user_id: Int): Flow<Result<Eng>>

    suspend fun getEngsByGroupId(group_id: Int): Flow<Result<Eng>>

    suspend fun postEng(eng: Eng): Flow<Result<Boolean>>

    suspend fun putEng(eng: Eng): Flow<Result<Boolean>>

    suspend fun deleteEng(eng_id: Int): Flow<Result<Nothing>>

    suspend fun getGroups(): Flow<Result<Group>>

    suspend fun postGroup(group: Group): Flow<Result<Boolean>>

    suspend fun putGroup(group: Group): Flow<Result<Boolean>>

    suspend fun deleteGroup(group_id: Int): Flow<Result<Boolean>>

    suspend fun postVnByEngId(vn: Vn): Flow<Result<Boolean>>

    suspend fun putVnByEngId(vn: Vn): Flow<Result<Boolean>>

    suspend fun deleteVn(vn_id: Int): Flow<Result<Boolean>>


}