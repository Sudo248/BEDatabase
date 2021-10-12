package com.nhom2.bedatabase.domain.repository

import com.nhom2.bedatabase.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun checkUserExist(user: User): Flow<Boolean>
    suspend fun identifyUser(user: User): Flow<Boolean>
}