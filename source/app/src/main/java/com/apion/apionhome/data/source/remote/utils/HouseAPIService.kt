package com.apion.apionhome.data.source.remote.utils

import com.apion.apionhome.data.source.remote.response_entity.AllHouseResponse
import com.apion.apionhome.data.source.remote.response_entity.HouseResponse
import com.apion.apionhome.utils.ApiEndPoint.PATH_HOUSES
import com.apion.apionhome.utils.ApiEndPoint.PATH_HOUSES_BY_ID
import com.apion.apionhome.utils.ApiEndPoint.PATH_PARAM_ID
import io.reactivex.rxjava3.core.Maybe
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface HouseAPIService {

    @GET(PATH_HOUSES)
    fun geHouses(): Maybe<AllHouseResponse>

    @GET(PATH_HOUSES_BY_ID)
    fun geHouseById(@Path(PATH_PARAM_ID) id: Int): Maybe<HouseResponse>

    @POST(PATH_HOUSES)
    fun createHouse(
        @Part attachments: List<MultipartBody.Part>,
        @Body house: RequestBody
    ): Maybe<HouseResponse>

    @POST(PATH_HOUSES_BY_ID)
    fun updateHouse(@Path(PATH_PARAM_ID) id: Int, @Body house: RequestBody): Maybe<HouseResponse>
}
