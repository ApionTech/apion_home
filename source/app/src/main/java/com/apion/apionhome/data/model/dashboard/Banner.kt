package com.apion.apionhome.data.model.dashboard

import com.google.gson.annotations.SerializedName

data class Banner(
    @SerializedName("image")
    val image:String,
    @SerializedName("link")
    val link:String,
    @SerializedName("title")
    val title:String,
    @SerializedName("created_at")
    val createdAt:String,
)
