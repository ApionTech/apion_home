package com.apion.apionhome.utils

import java.util.*

object ApiConfig {
    const val BASE_URL = "http://apionhome.com/api/v1/"
}

object ApiEndPoint {
    const val PATH_USERS = "users"
    const val PATH_USERS_BY_ID = "users/{id}"

    const val PATH_UPLOAD_AVATAR = "upload-avatar-user/"
    const val PATH_LOGIN = "login"
    const val PATH_UPDATE_PIN = "update-pincode"

    const val PATH_HOUSES = "houses"
    const val PATH_HOUSES_BY_ID = "houses/{id}"
    const val PATH_SELL_HOUSE = "sell-house"

    const val PATH_DASHBOARD = "dashboards"

    const val PART_PROVINCE = "province"
    const val PART_DISTRICT = "district"
    const val PART_PRICE_RANGE = "priceRange"
    const val PART_ACREAGE_RANGE = "acreageRange"
    const val PART_HOME_DIRECTION = "homeDirection"
    const val PART_TITLE = "title"
    const val PART_FRONT_WIDTH_RANGE = "frontWidthRange"
    const val PART_ATTACHMENT = "attachment[]"

    const val PATH_PARAM_ID = "id"
}
