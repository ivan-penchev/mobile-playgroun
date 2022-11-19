package com.penchev.exam.di

import androidx.room.Room
import com.penchev.exam.db.AppDatabase
import com.penchev.exam.repository.CryptoMarketRepository
import com.penchev.exam.service.CryptoService
import com.penchev.exam.util.NetworkUtil
import com.penchev.exam.viewmodel.CryptoMarketViewModel
import org.koin.android.compat.ScopeCompat.viewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    single { get<Retrofit>().create(CryptoService::class.java) }
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "crypto-market-db")
            .build()
    }
    single { get<AppDatabase>().cryptoMarketDao() }
    single  { CryptoMarketRepository(get(), get(), get()) }
    viewModel { CryptoMarketViewModel(get()) }
    factory { NetworkUtil(get()) }
}