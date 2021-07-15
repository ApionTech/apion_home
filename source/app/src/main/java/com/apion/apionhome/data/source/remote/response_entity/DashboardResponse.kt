package com.apion.apionhome.data.source.remote.response_entity

import com.apion.apionhome.data.model.Dashboard
import com.apion.apionhome.data.model.House
import com.google.gson.annotations.SerializedName

data class DashboardResponse(

    @SerializedName("data")
    val dashboard: Dashboard,

    @SerializedName("success")
    val isSuccess: Boolean,

    @SerializedName("message")
    val message: String,
)
