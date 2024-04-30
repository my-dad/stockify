package com.example.stockify

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stockify.databinding.ItemStockBinding
import com.example.stockify.model.StockDetails

class StockListAdapter(private val dataList: List<StockDetails>, private val onItemClick: (StockDetails) -> Unit) :
    RecyclerView.Adapter<StockListAdapter.ViewHolder>() {

    // ViewHolder class to hold the views
    inner class ViewHolder(private val binding: ItemStockBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(dataList[position])
                }
            }
        }

        fun bind(item: StockDetails) {
            binding.highPrice.text = item.highestPrice.toString()
            binding.symbol.text = item.symbol.toString()
            binding.lowPrice.text = item.lowestPrice.toString()
            binding.volume.text = item.volumeOfSymbol.toString()
            binding.noOfTrans.text = item.numberOfTransactions.toString()
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStockBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return dataList.size
    }
}
