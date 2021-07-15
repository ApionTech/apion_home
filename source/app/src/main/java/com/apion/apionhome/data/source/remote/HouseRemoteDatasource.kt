package com.apion.apionhome.data.source.remote

import com.apion.apionhome.data.model.House
import com.apion.apionhome.data.source.HouseDatasource
import com.apion.apionhome.data.source.remote.utils.HouseAPIService
import com.apion.apionhome.utils.ApiEndPoint.PART_ATTACHMENT
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Maybe
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.lang.Exception
import java.lang.IllegalArgumentException


class HouseRemoteDatasource(private val backend: HouseAPIService) : HouseDatasource.Remote {

    override fun getAllHouses(): Maybe<List<House>> = backend.geHouses().map { it.houses }

    override fun getHouseById(id: Int): Maybe<House> = backend.geHouseById(id).map { it.house }

    @Throws(IllegalArgumentException::class)
    override fun createHouse(images: List<String>, house: House): Maybe<House> {
        val imagesParts = mutableListOf<MultipartBody.Part>()
        images.forEach {
            val file = File(it)
            val imageRequestBody = RequestBody.create(MediaType.parse("image/*"), file)
            imagesParts.add(
                MultipartBody.Part.createFormData(PART_ATTACHMENT, file.name, imageRequestBody)
            )
        }

        val body = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            Gson().toJson(house)
        )
        return try {
            backend.createHouse(imagesParts, body).map {
                if (it.isSuccess) it.house else throw IllegalArgumentException(it.message)
            }
        } catch (exception: Exception) {
            Maybe.error(exception)
        }
    }

    @Throws(IllegalArgumentException::class)
    override fun updateHouse(house: House): Maybe<House> {
        val body = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            Gson().toJson(house)
        )
        return try {
            backend.updateHouse(house.id, body).map {
                if (it.isSuccess) it.house else throw IllegalArgumentException(it.message)
            }
        } catch (exception: Exception) {
            Maybe.error(exception)
        }
    }
}
