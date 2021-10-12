package com.nhom2.bedatabase.domain.use_case

import com.nhom2.bedatabase.domain.repository.ProEngRepository

class GetAllWordUseCase(var proEngRepository: ProEngRepository) {
    suspend operator fun invoke() = proEngRepository.getAllWord()
}