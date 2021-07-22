package com.apion.apionhome.data.model.dashboard

import com.apion.apionhome.data.model.House
import com.apion.apionhome.data.model.User
import com.google.gson.annotations.SerializedName

data class Dashboard(
    @SerializedName("feature")
    val feature: List<House>,
    @SerializedName("hanoi")
    val hanoiHouse: List<House>,
    @SerializedName("saigon")
    val saigonHouse: List<House>,
    @SerializedName("banner")
    val banners: List<Banner>,
    @SerializedName("user_online")
    val userOnline: List<User>,
    @SerializedName("total_user")
    val totalUser: Int,
) {
    fun getUserOnlineCount() = userOnline.size
}
