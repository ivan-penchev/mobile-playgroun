package com.penchev.exam.repository

import android.util.Log
import com.penchev.exam.db.dao.CryptoMarketDao
import com.penchev.exam.db.entity.CryptoMarket
import com.penchev.exam.model.CryptoResponse
import com.penchev.exam.service.CryptoService
import com.penchev.exam.util.NetworkUtil


class CryptoMarketRepository constructor(
    private val cryptoService: CryptoService,
    private val cryptoMarketDao: CryptoMarketDao,
    private val networkUtil: NetworkUtil
) {
    suspend fun getCryptoMarket(forCurrencyCode: String = "usd"): List<CryptoMarket> {
        return try {
            Log.i("CryptoMarketRepository", "fetching CryptoMarkets")

            val savedinDbMarkets = cryptoMarketDao.getCryptoMarkets()

            if (networkUtil.isOnline()) {
                Log.i("CryptoMarketRepository", "We have internet connection")
                if (savedinDbMarkets.isEmpty()) {
                    // We have no previous data, we do not need to reconcile difference between local and remote state
                    val cryptoMarketResponse = cryptoService.getCryptoMarkets(forCurrencyCode).execute().body()
                    val cryptoDbModel = cryptoMarketResponse?.map { mapResponseToDbModel(it) }
                    cryptoMarketDao.insertAll(cryptoDbModel ?: return arrayListOf())
                    cryptoDbModel ?:  arrayListOf()
                } else{
                    // Local state may have modification that are not present in remote
                    // try to merge both states, and save the combined version as the new local state.

                    // TODO: Reconcile state only via SQL
                    val cryptoMarketResponse = cryptoService.getCryptoMarkets(forCurrencyCode).execute().body()
                    val newCryptoDatabaseModels = cryptoMarketResponse?.map { mapResponseToDbModel(it) }
                    newCryptoDatabaseModels?.forEach { new ->
                        var oldCryptoDatabaseModel = savedinDbMarkets.find { it.id == new.id }
                        // We only know that there is one property that may be different
                        if (oldCryptoDatabaseModel != null && oldCryptoDatabaseModel.isLiked != new.isLiked) {
                            new.isLiked = oldCryptoDatabaseModel.isLiked
                        }
                    }
                    cryptoMarketDao.insertAll(newCryptoDatabaseModels ?: return arrayListOf())
                    return newCryptoDatabaseModels
                }
            } else {
                // we are not online, return what we have in local db
                Log.i("CryptoMarketRepository", "Limited internet connection, return local DB copy of the data")
                return savedinDbMarkets
            }
        } catch (e: Exception) {
            arrayListOf()
        }
    }

    suspend fun getCryptoMarketById(Id: String): CryptoMarket? {
        return try {
            return cryptoMarketDao.getCryptoMarketById(Id)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun updateCryptoMarket(cryptoMarket: CryptoMarket) {
        cryptoMarketDao.update(cryptoMarket)
    }


    private fun mapResponseToDbModel(response: CryptoResponse): CryptoMarket {
        return CryptoMarket(
            id = response.id,
            name = response.name ?: "",
            symbol = response.symbol,
            logoUrl = response.image,
            currentPrice = response.currentPrice,
            marketCap= response.marketCap,
            high24h= response.high24h,
            low24h= response.low24h,
            priceChangePercentage24h= response.priceChangePercentage24h,
            marketCapChange24h= response.marketCapChange24h,
            marketCapChangePercentage24h= response.marketCapChangePercentage24h,
        )
    }
}