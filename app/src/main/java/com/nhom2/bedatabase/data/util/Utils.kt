package com.nhom2.bedatabase.data.util

import com.nhom2.bedatabase.data.models.AccountRequest
import com.nhom2.bedatabase.data.models.AccountResponse
import com.nhom2.bedatabase.domain.common.Constants.ALGORITHM_HASH
import com.nhom2.bedatabase.domain.models.Account
import com.nhom2.bedatabase.domain.models.Vn
import java.security.MessageDigest

object Utils {

    var access_token: String? = null

    fun hash(password: String): String{
        val digest = MessageDigest.getInstance(ALGORITHM_HASH)
        val  sb = StringBuilder()
        val result = digest.digest(password.toByteArray(Charsets.UTF_8))
        for (i in result){
            sb.append(String.format("%02X",i))
        }
        return sb.toString()
    }
}

fun Account.toAccountRequest(): AccountRequest{
    return AccountRequest(
        email = this.email!!,
        password = this.password!!
    )
}

fun AccountResponse.toAccount(): Account{
    return Account(
        account_id = this.account_id,
        token = this.token
    )
}

//fun convertVnsToString(list: List<Vn>): String{
//    val res = ""
//    for(s in list){
//
//    }
//}


