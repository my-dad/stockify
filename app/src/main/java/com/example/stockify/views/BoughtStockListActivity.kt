package com.example.stockify.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockify.Application
import com.example.stockify.BaseActivity
import com.example.stockify.StockListAdapter
import com.example.stockify.database.dao.StockDetailsDao
import com.example.stockify.databinding.ActivityBoughtStockListBinding
import com.example.stockify.model.StockDetails
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BoughtStockListActivity : BaseActivity<ActivityBoughtStockListBinding>() {
    private lateinit var stockDao: StockDetailsDao
    var list: List<StockDetails> = listOf()
    private lateinit var adapter: StockListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stockDao = (application as Application).database.StockDetailsDao()
        getAllStocks()
        initUI()

    }

    private fun initUI() {
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = StockListAdapter(list, "details") {
            deleteBoughtStocks(it)
            updateRecyclerView(it)
        }
        binding.recyclerView.adapter = adapter
        adapter.submitList(list)

        if (list.isNullOrEmpty()){
            binding.NoStockFound.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        }else{
            binding.NoStockFound.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        }
    }

    private fun deleteBoughtStocks(stockDetails: StockDetails) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = stockDao.deleteBoughtStocks(stockDetails)
            withContext(Dispatchers.Main) {
                showSnackBar("Stocked Deleted Successfully")
            }

        }
    }

    override fun createBinding(): ActivityBoughtStockListBinding {
        return ActivityBoughtStockListBinding.inflate(LayoutInflater.from(this))
    }

    fun getAllStocks() {
        CoroutineScope(Dispatchers.IO).launch {
            list = stockDao.getAllBoughtStocks()
            adapter.submitList(list)
        }
    }

    fun showSnackBar(string: String){
        Snackbar.make(binding.root, string, Snackbar.LENGTH_SHORT).show()
    }

    private fun updateRecyclerView(stockDetails: StockDetails) {
        val updatedList = list.toMutableList()
        updatedList.remove(stockDetails)
        list = updatedList
        adapter.submitList(list)
        adapter.notifyDataSetChanged()
    }
}