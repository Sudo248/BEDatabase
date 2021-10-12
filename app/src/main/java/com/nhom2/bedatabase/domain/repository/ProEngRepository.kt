package com.nhom2.bedatabase.domain.repository

import com.nhom2.bedatabase.domain.models.Group
import com.nhom2.bedatabase.domain.models.Word
import kotlinx.coroutines.flow.Flow

interface ProEngRepository {
    suspend fun addNewWord(word: Word)
    suspend fun addGroup(group: Group)
    suspend fun getAllWord(): Flow<List<Word>>
    suspend fun getAllGroup(): Flow<List<Group>>
    suspend fun getRandomWord(): Flow<Word>
}