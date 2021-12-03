package com.nhom2.bedatabase.data.api

import com.nhom2.bedatabase.data.models.AccountChangePassword
import com.nhom2.bedatabase.data.models.AccountRequest
import com.nhom2.bedatabase.data.models.AccountResponse
import com.nhom2.bedatabase.data.util.Utils.access_token
import com.nhom2.bedatabase.domain.models.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("/signUp")
    suspend fun signUp(@Body account: AccountRequest): Response<AccountResponse>

    @POST("/signIn")
    suspend fun signIn(@Body account: AccountRequest): Response<AccountResponse>

    @PUT("/changePassword")
    suspend fun changePassword(@Body account: AccountChangePassword): AccountChangePassword

    @GET("/signOut")
    suspend fun signOut()

    @GET("/user/{id}")
    suspend fun getUser( @Path("id") user_id: Int, @Header("access_token") token: String? = access_token): Response<User>

    @PUT("/user")
    suspend fun putUser(@Body user: User, @Header("access_token") token: String? = access_token)

    @GET("/eng/user")
    suspend fun getEngsByUserId(@Query("user_id") user_id: Int, @Header("access_token") token: String? = access_token): Response<List<Eng>>

    @GET("/eng/group")
    suspend fun getEngsByGroupId(@Query("group_id") group_id: Int, @Header("access_token") token: String? = access_token): Response<List<Eng>>

    @GET("/eng/type")
    suspend fun getEngsByType(@Query("type") type: String, @Header("access_token") token: String? = access_token): Response<List<Eng>>

    @GET("/eng/{id}")
    suspend fun getEngById(@Path("id") id: Int, @Header("access_token") token: String? = access_token): Response<Eng>

    @PUT("/eng")
    suspend fun putEng(@Body eng: Eng, @Header("access_token") token: String? = access_token)

    @POST("/eng")
    suspend fun postEng(@Body eng: Eng, @Header("access_token") token: String? = access_token)

    @DELETE("/eng/{id}")
    suspend fun deleteEngById(@Path("id") id: Int, @Header("access_token") token: String? = access_token)

    @GET("/group")
    suspend fun getGroups(@Header("access_token") token: String? = access_token): List<Group>

    @GET("/group/{id}")
    suspend fun getGroupById(@Path("id") id: Int, @Header("access_token") token: String? = access_token): Response<Group>

    @PUT("/group")
    suspend fun putGroup(@Body group: Group, @Header("access_token") token: String? = access_token)

    @POST("/group")
    suspend fun postGroup(@Body group: Group, @Header("access_token") token: String? = access_token)

    @DELETE("/group/{id}")
    suspend fun deleteGroupById(@Path("id") id: Int, @Header("access_token") token: String? = access_token)

    @PUT("/vn")
    suspend fun putVn(@Body vn: Vn, @Header("access_token") token: String? = access_token)

    @POST("/vn")
    suspend fun postVn(@Body vn: Vn, @Header("access_token") token: String? = access_token)

    @DELETE("/vn/{id}")
    suspend fun deleteVn(@Path("id") id: Int, @Header("access_token") token: String? = access_token)

}