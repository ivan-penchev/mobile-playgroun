package com.penchev.exam.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.penchev.exam.db.dao.CryptoMarketDao
import com.penchev.exam.db.entity.CryptoMarket


@Database(entities = [CryptoMarket::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cryptoMarketDao(): CryptoMarketDao
}