package com.apion.apionhome.di

import com.apion.apionhome.data.repository.HouseRepository
import com.apion.apionhome.data.repository.HouseRepositoryImpl
import com.apion.apionhome.data.repository.UserRepository
import com.apion.apionhome.data.repository.UserRepositoryImpl
import com.apion.apionhome.data.source.HouseDatasource
import com.apion.apionhome.data.source.UserDatasource
import com.apion.apionhome.data.source.local.HouseLocalDatasource
import com.apion.apionhome.data.source.remote.HouseRemoteDatasource
import com.apion.apionhome.data.source.remote.UserRemoteDatasource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val userRepoModule = module {

    single<UserDatasource.Remote> { UserRemoteDatasource(get()) }

    single<UserRepository> { UserRepositoryImpl(get()) }
}

val houseRepoModule = module {
    single<HouseDatasource.Local> { HouseLocalDatasource(androidContext()) }

    single<HouseDatasource.Remote> { HouseRemoteDatasource(get()) }

    single<HouseRepository> { HouseRepositoryImpl(get(), get()) }
}
