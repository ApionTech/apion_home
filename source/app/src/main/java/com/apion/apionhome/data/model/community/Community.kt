package com.apion.apionhome.data.model.community

import com.apion.apionhome.data.model.GeneraEntity
import com.apion.apionhome.data.model.House
import com.google.gson.annotations.SerializedName

data class Community(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("district")
    val district: String,
    @SerializedName("short_description")
    val shortDesc: String,
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("cover")
    val cover: String,
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("updated_at")
    val updated_at: String,
    @SerializedName("houses")
    val houses: List<House>,
    @SerializedName("participants")
    val participants: List<Participant>,
) : GeneraEntity {

    override fun areItemsTheSame(newItem: GeneraEntity): Boolean =
        newItem is Community && this.id == newItem.id

    override fun areContentsTheSame(newItem: GeneraEntity): Boolean =
        newItem is Community && this == newItem
}
