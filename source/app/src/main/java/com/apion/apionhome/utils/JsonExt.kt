package com.apion.apionhome.utils

import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject

fun JSONObject.toMap(): Map<String, RequestBody> {

    return keys().asSequence().associateWith {
        val result = when (val value = this[it]) {
            is JSONArray -> value.toString()
            is JSONObject -> value.toString()
            else -> value.toString()
        }
        RequestBody.create(MediaType.parse("text/plain"), result)
    }
}
