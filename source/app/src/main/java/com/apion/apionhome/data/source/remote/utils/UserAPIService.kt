package com.apion.apionhome.data.source.remote.utils

import com.apion.apionhome.data.model.User
import com.apion.apionhome.data.source.remote.response_entity.AllUserResponse
import com.apion.apionhome.data.source.remote.response_entity.UserResponse
import com.apion.apionhome.utils.ApiEndPoint.PATH_LOGIN
import com.apion.apionhome.utils.ApiEndPoint.PATH_PARAM_ID
import com.apion.apionhome.utils.ApiEndPoint.PATH_USERS
import com.apion.apionhome.utils.ApiEndPoint.PATH_USERS_BY_ID
import io.reactivex.rxjava3.core.Maybe
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserAPIService {

    @GET(PATH_USERS)
    fun geUsers(): Maybe<AllUserResponse>

    @GET(PATH_USERS_BY_ID)
    fun geUserById(@Path(PATH_PARAM_ID) id: Int): Maybe<UserResponse>

    @POST(PATH_USERS)
    fun createUser(@Body user: RequestBody): Maybe<UserResponse>

    @POST(PATH_USERS_BY_ID)
    fun updateUser(@Path(PATH_PARAM_ID) id: Int, @Body user: RequestBody): Maybe<UserResponse>

    @POST(PATH_LOGIN)
    fun login(@Body phone: RequestBody): Maybe<UserResponse>
}
