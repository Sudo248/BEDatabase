package com.nhom2.bedatabase.domain.use_case

import com.nhom2.bedatabase.domain.models.User
import com.nhom2.bedatabase.domain.repository.UserRepository

class CheckUserExist(var userRepository: UserRepository) {
    suspend operator fun invoke(user: User) = userRepository.checkUserExist(user)
}