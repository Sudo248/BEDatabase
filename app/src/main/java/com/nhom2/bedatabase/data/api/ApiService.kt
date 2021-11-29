package com.nhom2.bedatabase.data.api

import com.nhom2.bedatabase.data.models.AccountRequest
import com.nhom2.bedatabase.data.models.AccountResponse
import com.nhom2.bedatabase.data.util.Utils.access_token
import com.nhom2.bedatabase.domain.models.User
import retrofit2.http.*

interface ApiService {

    @GET("/signUp")
    suspend fun signUp(@Body account: AccountRequest): AccountResponse

    @GET("/signIn")
    suspend fun signIn(@Body account: AccountRequest): AccountResponse

    @GET("/signOut")
    suspend fun signOut()

    @GET("/user/{id}")
    suspend fun getUser( @Path("id") user_id: Int, @Header("access_token") token: String? = access_token)

    @PUT("/user")
    suspend fun putUser(@Body user: User, @Header("access_token") token: String? = access_token)




}