package com.nhom2.bedatabase.domain.repository

import com.nhom2.bedatabase.domain.common.Result
import com.nhom2.bedatabase.domain.models.*
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    suspend fun signIn(account: Account): Flow<Result<Boolean>>

    suspend fun signUp(account: Account): Flow<Result<Boolean>>

    suspend fun changePassword(account_id: Int, oldPassword: String, newPassword: String): Flow<Result<Boolean>>

    suspend fun signOut()

    suspend fun signInWithToken(): Flow<Result<Boolean>>

    suspend fun getUser(): Flow<Result<User>>

    suspend fun getUserFromSharePref(): User?

    suspend fun putUser(user: User): Flow<Result<Boolean>>

    suspend fun getEngsByUserId(user_id: Int): Flow<Result<List<Eng>>>

    suspend fun getEngsByGroupId(group_id: Int): Flow<Result<List<Eng>>>

    suspend fun postEng(eng: Eng): Flow<Result<Int>>

    suspend fun putEng(eng: Eng): Flow<Result<Boolean>>

    suspend fun deleteEng(eng_id: Int): Flow<Result<Boolean>>

    suspend fun getGroups(): Flow<Result<List<Group>>>

    suspend fun postGroup(group: Group): Flow<Result<Boolean>>

    suspend fun putGroup(group: Group): Flow<Result<Boolean>>

    suspend fun deleteGroup(group_id: Int): Flow<Result<Boolean>>

    suspend fun postVnByEngId(vn: Vn): Flow<Result<Boolean>>

    suspend fun putVnByEngId(vn: Vn): Flow<Result<Boolean>>

    suspend fun deleteVn(vn_id: Int): Flow<Result<Boolean>>

}