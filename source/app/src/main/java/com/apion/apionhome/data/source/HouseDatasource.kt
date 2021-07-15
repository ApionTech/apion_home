package com.apion.apionhome.data.source

import com.apion.apionhome.data.model.House
import io.reactivex.rxjava3.core.Maybe

interface HouseDatasource {


    interface Remote {

        fun getAllHouses(): Maybe<List<House>>
        fun getHouseById(id: Int): Maybe<House>

        fun createHouse(images: List<String>, house: House): Maybe<House>
        fun updateHouse(house: House): Maybe<House>
    }
}
