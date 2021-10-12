package com.nhom2.bedatabase.domain.use_case

import com.nhom2.bedatabase.domain.models.Group
import com.nhom2.bedatabase.domain.repository.ProEngRepository

class AddGroupUserCase(var proEngRepository: ProEngRepository) {
    suspend operator fun invoke(group: Group) = proEngRepository.addGroup(group)
}