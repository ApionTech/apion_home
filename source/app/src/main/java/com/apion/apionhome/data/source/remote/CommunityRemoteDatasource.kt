package com.apion.apionhome.data.source.remote

import com.apion.apionhome.data.model.User
import com.apion.apionhome.data.model.community.Community
import com.apion.apionhome.data.model.community.Participant
import com.apion.apionhome.data.source.CommunityDatasource
import com.apion.apionhome.data.source.UserDatasource
import com.apion.apionhome.data.source.remote.utils.CommunityAPIService
import com.apion.apionhome.data.source.remote.utils.UserAPIService
import com.apion.apionhome.utils.ApiEndPoint
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.File
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.util.*

class CommunityRemoteDatasource(private val backend: CommunityAPIService) :
    CommunityDatasource.Remote {

    @Throws(IllegalArgumentException::class)
    override fun login(phone: String, pinCode: String): Maybe<User> {
        val json = JsonObject().apply {
            addProperty("phone", phone)
        }

        val body = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            Gson().toJson(json)
        )

        return try {
            backend.login(body).map {
                if (it.isSuccess) {
                    if (it.user.pincode == pinCode) {
                        it.user
                    } else {
                        throw IllegalArgumentException(AUTHEN_EXCEPTION)
                    }
                } else {
                    throw IllegalArgumentException(it.message)
                }
            }
        } catch (exception: Exception) {
            Maybe.error(exception)
        }
    }

    override fun getAllCommunities(): Maybe<List<Community>> = backend.getAllCommunities().map {
        it.communities
    }

    override fun getCommunityById(id: Int): Maybe<Community> =
        backend.getCommunityById(id).map { it.community }

    override fun createCommunity(community: Community): Maybe<Community> {
        val json = JsonObject().apply {
            addProperty("phone", phone)
        }

        val body = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            Gson().toJson(json)
        )
    }

    override fun updateCommunity(community: Community): Maybe<Community> {
        TODO("Not yet implemented")
    }

    override fun getAllParticipants(): Maybe<List<Participant>> {
        TODO("Not yet implemented")
    }

    override fun getParticipantById(id: Int): Maybe<Participant> {
        TODO("Not yet implemented")
    }

    override fun createParticipant(userId: Int, communityId: Int): Maybe<Participant> {
        TODO("Not yet implemented")
    }

    override fun updateParticipant(userId: Int, communityId: Int): Maybe<Participant> {
        TODO("Not yet implemented")
    }

    override fun leaveCommunity(userId: Int, communityId: Int): Completable {
        TODO("Not yet implemented")
    }
}
