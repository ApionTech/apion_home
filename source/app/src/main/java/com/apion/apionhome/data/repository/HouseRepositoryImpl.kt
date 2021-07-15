package com.apion.apionhome.data.repository

import com.apion.apionhome.data.model.House
import com.apion.apionhome.data.source.HouseDatasource
import io.reactivex.rxjava3.core.Maybe
import java.lang.Exception


class HouseRepositoryImpl(private val remote: HouseDatasource.Remote) : HouseRepository {

    override fun getAllHouses(): Maybe<List<House>> = remote.getAllHouses()

    override fun getHouseById(id: Int): Maybe<House> = remote.getHouseById(id)

    override fun createHouse(images: List<String>, house: House): Maybe<House> {
        return try {
            remote.createHouse(images, house)
        } catch (exception: Exception) {
            Maybe.error(exception)
        }
    }

    override fun updateHouse(house: House): Maybe<House> {
        return try {
            remote.updateHouse(house)
        } catch (exception: Exception) {
            Maybe.error(exception)
        }
    }
}
