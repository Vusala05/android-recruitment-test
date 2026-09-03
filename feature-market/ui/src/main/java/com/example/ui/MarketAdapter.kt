package com.example.feature.market.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.core.data.R
import com.example.data.domain.response.MarketItemResponseDO
import com.example.data.domain.util.TrendType
import com.example.data.util.toTime
import com.example.feature.market.ui.databinding.ItemMarketBinding

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
            tvTimestamp.text = item.timestamp.toTime()

            val context = root.context

            when (item.trend) {
                TrendType.UP -> {
                    tvTrend.text = ContextCompat.getString(context,R.string.up)
                    tvTrend.background = ContextCompat.getDrawable(context, R.drawable.bg_trend_up)
                    tvTrend.setTextColor(ContextCompat.getColor(context, R.color.trend_up))
                }
                TrendType.DOWN -> {
                    tvTrend.text = ContextCompat.getString(context,R.string.down)
                    tvTrend.background = ContextCompat.getDrawable(context, R.drawable.bg_trend_down)
                    tvTrend.setTextColor(ContextCompat.getColor(context, R.color.trend_down))
                }
                TrendType.UNKNOWN -> {
                    tvTrend.text = "-"
                    tvTrend.background = null
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class MarketHolder(val binding: ItemMarketBinding) : RecyclerView.ViewHolder(binding.root)
}