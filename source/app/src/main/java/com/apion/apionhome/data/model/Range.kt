package com.apion.apionhome.data.model

data class Range(
    val min: Int = -1,
    val max: Int = -1,
    val dram: String = ""
) {
    override fun toString(): String {
        return "from$min" + "to$max" + dram
    }
}
