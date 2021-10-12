package com.nhom2.bedatabase.domain.use_case

import com.nhom2.bedatabase.domain.repository.ProEngRepository
import com.nhom2.bedatabase.domain.models.Word

class AddWordUseCase(var proEngRepository: ProEngRepository?) {
    suspend operator fun invoke(word: Word) = proEngRepository?.addNewWord(word)
}