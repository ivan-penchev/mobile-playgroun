package com.penchev.exam.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.penchev.exam.R
import com.penchev.exam.databinding.CryptoMarketListItemBinding
import com.penchev.exam.db.entity.CryptoMarket
import com.penchev.exam.fragment.CryptoMarketListFragment
import com.penchev.exam.fragment.CryptoMarketListFragmentDirections


class CryptoMarketListAdapter(private val cryptoMarkets: List<CryptoMarket>) :
    RecyclerView.Adapter<CryptoMarketListAdapter.CryptoMarketsViewHolder>() {

    class CryptoMarketsViewHolder(val binding: CryptoMarketListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoMarketsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CryptoMarketListItemBinding.inflate(layoutInflater, parent, false)

        return CryptoMarketsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoMarketsViewHolder, position: Int) {
        val currentCryptoMarket = cryptoMarkets[position]
        holder.binding.apply {
            coinName = currentCryptoMarket.name
            coinSymbol = currentCryptoMarket.symbol
            coinPrice = "Price ${currentCryptoMarket.currentPrice}"
            ivLiked.visibility = if (currentCryptoMarket.isLiked) View.VISIBLE else View.GONE

            Glide
                // context to use for requesting the image
                .with(root.context)
                // URL to load the asset from
                .load(currentCryptoMarket.logoUrl)
                .centerCrop()
                // image to be shown until online asset is downloaded
                .placeholder(R.drawable.ic_launcher_foreground)
                // ImageView to load the online asset into when ready
                .into(ivCryptoMarketLogo)

            root.setOnClickListener {
            val action = CryptoMarketListFragmentDirections
                .actionCryptoMarketListFragmentToCryptoMarketDetailsFragment(currentCryptoMarket.id)
                root.findNavController().navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return cryptoMarkets.size
    }
}