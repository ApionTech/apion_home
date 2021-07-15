//package com.fsoft.weatherapp.data.source.local
//
//import android.content.Context
//import com.fsoft.weatherapp.data.model.LocationApp
//import com.apion.apionhome.data.source.UserDatasource
//import com.fsoft.weatherapp.data.source.remote.response_entity.WeatherResponse
//import com.google.gson.Gson
//import com.google.gson.JsonIOException
//import io.reactivex.rxjava3.core.Completable
//import io.reactivex.rxjava3.core.Maybe
//import java.io.BufferedReader
//import java.io.File
//import java.io.IOException
//
//class WeatherLocalDatasource(private val context: Context) : UserDatasource.Local {
//
//    override fun setResponseCache(response: WeatherResponse): Completable {
//        return setContentFile(Gson().toJson(response), FILE_NAME_RESPONSE)
//    }
//
//    override fun getResponseCache(): Maybe<WeatherResponse> {
//        return getContentFile(FILE_NAME_RESPONSE).map {
//            Gson().fromJson(it, WeatherResponse::class.java)
//        }
//    }
//
//    override fun setLocationCache(locationApp: LocationApp): Completable {
//        return setContentFile(Gson().toJson(locationApp), FILE_NAME_LOCATION)
//    }
//
//    override fun setLastTimeUpdate(time: String): Completable {
//        return setContentFile(time, FILE_NAME_TIME)
//    }
//
//    override fun getLocationCache(): Maybe<LocationApp> {
//        println("datasource get location cache")
//        return getContentFile(FILE_NAME_LOCATION).zipWith(getContentFile(FILE_NAME_TIME),
//            { location, time ->
//                Gson().fromJson(location, LocationApp::class.java).also {
//                    it.timeUpdate = time
//                }
//            })
//    }
//
//    private fun setContentFile(
//        content: String,
//        fileName: String,
//    ): Completable {
//        return try {
//            val file = File(context.filesDir, fileName)
//            file.writeText(content)
//            Completable.complete()
//        } catch (ioException: IOException) {
//            ioException.printStackTrace()
//            Completable.error(ioException)
//        } catch (jsonException: JsonIOException) {
//            jsonException.printStackTrace()
//            Completable.error(jsonException)
//        }
//    }
//
//    private fun getContentFile(fileName: String): Maybe<String> {
//        return try {
//            val file = File(context.filesDir, fileName)
//            if (file.exists()) {
//                val bufferedReader: BufferedReader = file.bufferedReader()
//                val inputString = bufferedReader.readText()
//                println("content $inputString $fileName")
//                Maybe.just(inputString)
//            } else {
//                Maybe.empty()
//            }
//        } catch (ioException: IOException) {
//            ioException.printStackTrace()
//            Maybe.error(ioException)
//        } catch (jsonException: JsonIOException) {
//            jsonException.printStackTrace()
//            Maybe.error(jsonException)
//        }
//    }
//
//    companion object {
//        private const val FILE_NAME_RESPONSE = "response.json"
//        private const val FILE_NAME_LOCATION = "location.json"
//        private const val FILE_NAME_TIME = "time_update.txt"
//    }
//}
