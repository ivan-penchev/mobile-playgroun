package com.penchev.exam.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.penchev.exam.CryptoMarketViewModelFactory
import com.penchev.exam.databinding.ActivityMainBinding
import com.penchev.exam.viewmodel.CryptoMarketViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.compat.ScopeCompat.viewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val cryptoMarketViewModel: CryptoMarketViewModel by inject<CryptoMarketViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeData()
    }

    private fun observeData() {
        GlobalScope.launch {
            cryptoMarketViewModel.showLoadingIndicator?.collect {
                Log.i("Main activity", "Loading indicator received ${it}")
                runOnUiThread {
                    binding.loadingSpinner.visibility = if (it) View.VISIBLE else View.GONE
                }
            }
        }
    }
}