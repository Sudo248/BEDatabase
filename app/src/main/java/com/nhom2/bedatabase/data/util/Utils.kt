package com.nhom2.bedatabase.data.util

import com.nhom2.bedatabase.data.models.AccountRequest
import com.nhom2.bedatabase.data.models.AccountResponse
import com.nhom2.bedatabase.domain.common.Constants.ALGORITHM_HASH
import com.nhom2.bedatabase.domain.models.Account
import com.nhom2.bedatabase.domain.models.Vn
import java.security.MessageDigest

import android.graphics.Bitmap

import android.util.Base64

import java.io.ByteArrayOutputStream
import android.graphics.BitmapFactory













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

    fun bitmapToString(image: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)

        return Base64.encodeToString(
            outputStream.toByteArray(),
            Base64.DEFAULT
        )
    }

    fun stringToBitmap(encodedImage: String): Bitmap {
        val decodedString: ByteArray = Base64.decode(encodedImage, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
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


