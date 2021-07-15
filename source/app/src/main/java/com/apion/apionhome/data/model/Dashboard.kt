package com.apion.apionhome.data.model

import android.net.wifi.hotspot2.pps.HomeSp
import com.google.gson.annotations.SerializedName

data class Dashboard(
    @SerializedName("feature")
    val feature: List<House>,
    @SerializedName("hanoi")
    val hanoiHouse: List<House>,
    @SerializedName("saigon")
    val saigonHouse: List<House>
) {
}
