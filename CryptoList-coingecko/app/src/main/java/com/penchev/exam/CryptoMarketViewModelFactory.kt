package com.penchev.exam

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.penchev.exam.repository.CryptoMarketRepository
import com.penchev.exam.viewmodel.CryptoMarketViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
//
//class CryptoMarketViewModelFactory: ViewModelProvider.Factory, KoinComponent {
//        private val searchRepo: CryptoMarketRepository by inject()
//        override fun <T : ViewModel> create(modelClass: Class<T>): T = CryptoMarketViewModel(searchRepo) as T
//}