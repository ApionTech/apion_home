package com.apion.apionhome.di

import com.apion.apionhome.data.repository.UserRepository
import com.apion.apionhome.data.repository.UserRepositoryImpl
import com.apion.apionhome.data.source.UserDatasource
import com.apion.apionhome.data.source.remote.UserRemoteDatasource
import org.koin.dsl.module

val gitHubRepoModule = module {

    single<UserDatasource.Remote> { UserRemoteDatasource(get()) }


    single<UserRepository> { UserRepositoryImpl(get(), get()) }
}
