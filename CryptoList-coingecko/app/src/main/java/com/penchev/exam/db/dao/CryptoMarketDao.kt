package com.penchev.exam.db.dao

import androidx.room.*
import com.penchev.exam.db.entity.CryptoMarket


@Dao
interface CryptoMarketDao {

    @Query("SELECT * FROM crypto_markets")
    suspend fun getCryptoMarkets(): List<CryptoMarket>

    @Query("SELECT * FROM crypto_markets WHERE id=:id")
    suspend fun getCryptoMarketById(id: String): CryptoMarket

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(countries: List<CryptoMarket>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(country: CryptoMarket)
}