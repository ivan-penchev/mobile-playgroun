package com.penchev.exam.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.penchev.exam.db.entity.CryptoMarket
import com.penchev.exam.repository.CryptoMarketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CryptoMarketViewModel(
    private val cryptoMarketRepository: CryptoMarketRepository
) : ViewModel() {
    private val cryptoMarketListStateFlow = MutableStateFlow<List<CryptoMarket>>(arrayListOf())

    val cryptoMarketList: StateFlow<List<CryptoMarket>> = cryptoMarketListStateFlow.asStateFlow()

    private val showLoadingIndicatorStateFlow = MutableStateFlow(false)

    val showLoadingIndicator: StateFlow<Boolean> = showLoadingIndicatorStateFlow.asStateFlow()

    private val selectedCryptoMarketStateFlow = MutableStateFlow<CryptoMarket?>(null)

    val selectedCryptoMarket: StateFlow<CryptoMarket?> = selectedCryptoMarketStateFlow.asStateFlow()

    suspend fun getCryptoMarkets() {
        Log.i("CryptoMarketViewModel", "Calling repository to fetch data")
        showLoadingIndicatorStateFlow.value = true
        val cryptoMarkets = cryptoMarketRepository.getCryptoMarket()
        Log.i("CryptoMarketViewModel", "Repository returned data")
        cryptoMarketListStateFlow.value = cryptoMarkets
        showLoadingIndicatorStateFlow.value = false
        Log.i("CryptoMarketViewModel", "Updated cryptoMarketListStateFlow with data from repository")
    }

    suspend fun getCryptoMarketsById(id: String) {
        Log.i("CryptoMarketViewModel", "Calling repository to fetch data for Market with ID ${id}")
        showLoadingIndicatorStateFlow.value = true
        val market = cryptoMarketRepository.getCryptoMarketById(id)
        Log.i("CryptoMarketViewModel", "Repository returned data")
        selectedCryptoMarketStateFlow.value = market
        showLoadingIndicatorStateFlow.value = false
        Log.i("CryptoMarketViewModel", "Updated selectedCryptoMarketStateFlow with data from repository")

    }

    suspend fun updateCryptoMarket(cryptoMarket: CryptoMarket) {
        Log.i("CryptoMarketViewModel", "Calling repository to update cryptomarket")
        showLoadingIndicatorStateFlow.value = true
        val market = cryptoMarketRepository.updateCryptoMarket(cryptoMarket)
        Log.i("CryptoMarketViewModel", "Repository updated data")
        showLoadingIndicatorStateFlow.value = false
    }

}