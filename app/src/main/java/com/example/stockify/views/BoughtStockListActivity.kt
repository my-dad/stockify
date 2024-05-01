package com.example.stockify.views

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockify.Application
import com.example.stockify.BaseActivity
import com.example.stockify.R
import com.example.stockify.StockListAdapter
import com.example.stockify.database.dao.StockDetailsDao
import com.example.stockify.databinding.ActivityBoughtStockListBinding
import com.example.stockify.model.StockDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BoughtStockListActivity : BaseActivity<ActivityBoughtStockListBinding>() {
    private lateinit var stockDao: StockDetailsDao
    var list: List<StockDetails> = listOf()
    private lateinit var adapter: StockListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stockDao = (application as Application).database.StockDetailsDao()

        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = StockListAdapter(list) {

        }
        binding.recyclerView.adapter = adapter
        getAllStocks()

        adapter.submitList(list)
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
}