package com.penchev.exam.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.penchev.exam.CryptoMarketViewModelFactory
import com.penchev.exam.activity.MainActivity
import com.penchev.exam.adapter.CryptoMarketListAdapter
import com.penchev.exam.databinding.FragmentCryptoMarketListBinding
import com.penchev.exam.viewmodel.CryptoMarketViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class CryptoMarketListFragment : Fragment() {
    private lateinit var binding: FragmentCryptoMarketListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCryptoMarketListBinding.inflate(inflater, container, false)

        GlobalScope.launch {
            (activity as MainActivity)?.cryptoMarketViewModel?.getCryptoMarkets()
        }
        observeData()
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun observeData() {
        GlobalScope.launch {
            (activity as MainActivity)?.cryptoMarketViewModel?.cryptoMarketList?.collect {
                (activity as MainActivity)?.runOnUiThread {
                    val cryptoMarkets = it
                    val sortedCryptoMarketsByMarketCap = cryptoMarkets.sortedByDescending { it.marketCap }
                    val sortedCryptoMarkets = sortedCryptoMarketsByMarketCap.sortedByDescending { it.isLiked }
                    val adapter = CryptoMarketListAdapter(sortedCryptoMarkets)
                    binding.cryptoList.adapter = adapter
                    binding.tvCryptoCount.text = "Crypto Markets: ${it.size}"
                }
            }
        }
    }
}