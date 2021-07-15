package com.apion.apionhome.data.source

import com.apion.apionhome.data.model.User
import io.reactivex.rxjava3.core.Maybe

interface UserDatasource {


    interface Remote {

        fun getAllUsers(): Maybe<List<User>>
        fun getUserById(id: Int): Maybe<User>

        fun createUser(user: User): Maybe<User>
        fun updateUser(user: User): Maybe<User>
        fun login(phone: String): Maybe<User>
    }
}
