package com.penchev.exam.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "crypto_markets")
data class CryptoMarket (
    @PrimaryKey val id: String,
    @ColumnInfo(name = "symbol") val symbol: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "logoUrl") val logoUrl: String,
    @ColumnInfo(name = "currentPrice") val currentPrice: Double,
    @ColumnInfo(name = "marketCap") val marketCap: Long,
    @ColumnInfo(name = "high24h") val high24h: Double,
    @ColumnInfo(name = "low24h") val low24h: Double,
    @ColumnInfo(name = "priceChangePercentage24h") val priceChangePercentage24h: Double,
    @ColumnInfo(name = "marketCapChange24h") val marketCapChange24h: Double,
    @ColumnInfo(name = "marketCapChangePercentage24h") val marketCapChangePercentage24h: Double,
    @ColumnInfo(name = "isLiked") var isLiked: Boolean = false,
)

