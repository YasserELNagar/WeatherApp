package com.example.weatherapp.features.weatherHistory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core.databinding.ViewWeatherHistoryListItemBinding
import com.example.core.util.loadOfflineImage
import com.example.domain.model.WeatherItem
import com.example.weatherapp.R


class WeatherHistoryItemsAdapter(private val clickListener:(position:Int,item:WeatherItem?,actionType:WeatherItemActionType)->Unit ) :
    ListAdapter<WeatherItem, WeatherHistoryItemsAdapter.WeatherHistoryItemViewHolder>(
        DiffUtilCallback
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeatherHistoryItemViewHolder {
        return WeatherHistoryItemViewHolder.from(parent, clickListener)
    }

    override fun onBindViewHolder(holder: WeatherHistoryItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    
    class WeatherHistoryItemViewHolder(
        private val binding: ViewWeatherHistoryListItemBinding,
        private val clickListener: (position:Int,item:WeatherItem?,actionType:WeatherItemActionType)-> Unit

    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WeatherItem?) {
            val context =binding.tvTemp.context
            binding.tvTemp.text = context.getString(R.string.heat_degree_format,item?.temp.toString())
            binding.tvCondition.text = item?.description
            binding.tvLocation.text = item?.cityName
            binding.ivImage.loadOfflineImage(item?.photoPath)
            binding.btnShare.setOnClickListener {
                clickListener(adapterPosition,item,WeatherItemActionType.SHARE)
            }
            binding.root.setOnClickListener {
                clickListener(adapterPosition,item,WeatherItemActionType.VIEW)
            }

        }

        companion object {
            fun from(
                parent: ViewGroup,
                clickListener: (position:Int,item:WeatherItem?,actionType:WeatherItemActionType)-> Unit
            ): WeatherHistoryItemViewHolder {
                return WeatherHistoryItemViewHolder(
                    ViewWeatherHistoryListItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    clickListener
                )
            }
        }

    }

    companion object DiffUtilCallback : DiffUtil.ItemCallback<WeatherItem>() {
        override fun areItemsTheSame(
            oldItem: WeatherItem,
            newItem: WeatherItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: WeatherItem,
            newItem: WeatherItem
        ): Boolean {
            return oldItem == newItem
        }

    }

}


