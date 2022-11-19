package com.penchev.exam.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.penchev.exam.R
import com.penchev.exam.activity.MainActivity
import com.penchev.exam.databinding.FragmentCryptoMarketDetailsBinding
import com.penchev.exam.viewmodel.CryptoMarketViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class CryptoMarketDetailsFragment : Fragment() {
    private lateinit var binding: FragmentCryptoMarketDetailsBinding
    private lateinit var args: CryptoMarketDetailsFragmentArgs
    private val cryptoMarketViewModel by activityViewModel<CryptoMarketViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args = navArgs<CryptoMarketDetailsFragmentArgs>().value
        GlobalScope.launch {
            cryptoMarketViewModel?.getCryptoMarketsById(
                args.cryptoMarketId ?: return@launch
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCryptoMarketDetailsBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun observeData() {
        GlobalScope.launch {
            cryptoMarketViewModel?.selectedCryptoMarket?.collect {
                binding.cryptoMarket = it ?: return@collect
                setDataBinding()
                (activity as? MainActivity)?.runOnUiThread {
                    Glide
                        .with(context ?: return@runOnUiThread)
                        .load(it.logoUrl)
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(binding.ivLogo)
                }
            }
        }
    }

    private fun setDataBinding() {
        binding.cryptoMarket ?: return

        var cryptoMarket = binding.cryptoMarket

        if (binding.cryptoMarket?.isLiked == true) {
            binding.btnLike.setImageResource(android.R.drawable.star_big_on)
        } else {
            binding.btnLike.setImageResource(android.R.drawable.star_big_off)
        }

        binding.tvCoinMarketCap.text = "Market cap ${cryptoMarket?.marketCap}"
        binding.tvCoinHigh24.text = "24 hours high ${cryptoMarket?.high24h}"
        binding.tvCoinMarketCapChangePercentage24h.text = "Market cap change percent 24h ${cryptoMarket?.marketCapChangePercentage24h}"
        if (cryptoMarket?.marketCapChangePercentage24h!! <= 0){
            binding.tvCoinMarketCapChangePercentage24h.setTextColor(Color.parseColor("#FF0000"))
        } else {
            binding.tvCoinMarketCapChangePercentage24h.setTextColor(Color.parseColor("#00FF00"))
        }
        binding.tvCoinPriceChangePercentage24h.text = "Price change 24h ${cryptoMarket?.priceChangePercentage24h}"
        if (cryptoMarket?.priceChangePercentage24h!! <= 0){
            binding.tvCoinPriceChangePercentage24h.setTextColor(Color.parseColor("#FF0000"))
        } else {
            binding.tvCoinPriceChangePercentage24h.setTextColor(Color.parseColor("#00FF00"))
        }

        binding.tvCoinPrice.text = "Price ${cryptoMarket?.currentPrice}"

        binding.tvCoinLowest24h.text = "Lowest 24h: ${cryptoMarket.low24h}"

        binding.btnLike.setOnClickListener {

            cryptoMarket?.isLiked = cryptoMarket?.isLiked != true

            if (cryptoMarket?.isLiked == true) {
                binding.btnLike.setImageResource(android.R.drawable.star_big_on)
            } else {
                binding.btnLike.setImageResource(android.R.drawable.star_big_off)
            }

            GlobalScope.launch {
                cryptoMarketViewModel?.updateCryptoMarket(
                    cryptoMarket ?: return@launch
                )
            }
        }
    }
}