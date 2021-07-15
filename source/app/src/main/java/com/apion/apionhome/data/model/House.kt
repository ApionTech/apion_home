package com.apion.apionhome.data.model

import com.fsoft.weatherapp.data.model.GeneraEntity
import com.google.gson.annotations.SerializedName

data class House(
    @SerializedName("id")
    val id: Int,
    @SerializedName("news_type")
    val newsType: String,
    @SerializedName("houseType")
    val houseType: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("province")
    val province: String,
    @SerializedName("district")
    val district: String,
    @SerializedName("ward")
    val ward: String,
    @SerializedName("street")
    val street: String,
    @SerializedName("detail_address")
    val address: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("acreage")
    val acreage: Int,
    @SerializedName("homeDirection")
    val homeDirection: String,
    @SerializedName("bedrooms")
    val bedrooms: Int,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("seller_id")
    val sellerId: Int?,
    @SerializedName("owner")
    val owner: User,
    @SerializedName("seller")
    val seller: User?,
    @SerializedName("images")
    var images: List<String>,
    @SerializedName("related_houses")
    val relatedHouses: List<House>?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
) : GeneraEntity {

    override fun areItemsTheSame(newItem: GeneraEntity): Boolean =
        newItem is House && this.id == newItem.id

    override fun areContentsTheSame(newItem: GeneraEntity): Boolean =
        newItem is House && this == newItem
}
