package com.example.socketapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.socketapp.R
import com.example.socketapp.databinding.ItemMarketBinding
import com.example.socketapp.domain.response.MarketItemResponseDO

class MarketAdapter: RecyclerView.Adapter<MarketAdapter.MarketHolder>() {

    private val differCallBack = object : DiffUtil.ItemCallback<MarketItemResponseDO>() {
        override fun areItemsTheSame(oldItem: MarketItemResponseDO, newItem: MarketItemResponseDO): Boolean {
            return oldItem.symbol == newItem.symbol
        }

        override fun areContentsTheSame(oldItem: MarketItemResponseDO, newItem: MarketItemResponseDO): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, differCallBack)
    fun submitList(list: List<MarketItemResponseDO>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketHolder {
        val binding = ItemMarketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MarketHolder(binding)
    }

    override fun onBindViewHolder(holder: MarketHolder, position: Int) {
        val item = differ.currentList[position]
        holder.binding.apply {
            tvSymbol.text = item.symbol
            tvPrice.text = "$${item.price}"
            tvHighPrice.text = item.highPrice
            tvLowPrice.text = item.lowPrice
            tvOpenPrice.text = item.openPrice
            tvVolume.text = item.volume.toString()
            tvTimestamp.text = item.timestamp

            val isUp = item.trend.equals("up", ignoreCase = true)
            tvTrend.text = if (isUp) "UP" else "DOWN"

            if (isUp) {
                tvTrend.background = ContextCompat.getDrawable(root.context, R.drawable.bg_trend_up)
                tvTrend.setTextColor("#10B981".toColorInt())
            } else {
                tvTrend.background = ContextCompat.getDrawable(root.context, R.drawable.bg_trend_down)
                tvTrend.setTextColor("#EF4444".toColorInt())
            }
        }


    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class MarketHolder(val binding: ItemMarketBinding) : RecyclerView.ViewHolder(binding.root)
}