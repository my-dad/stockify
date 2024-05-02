package com.example.stockify.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockify.Application
import com.example.stockify.BaseActivity
import com.example.stockify.Constants.API_KEY
import com.example.stockify.Constants.SHARED_PREF_NAME
import com.example.stockify.PreferenceManager
import com.example.stockify.StockListAdapter
import com.example.stockify.database.dao.StockDetailsDao
import com.example.stockify.databinding.ActivityStockHomeBinding
import com.example.stockify.model.StockDetails
import com.example.stockify.model.StockPayload
import com.example.stockify.previousDate
import com.example.stockify.service.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar
import android.R.id
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import com.example.stockify.R


class StockHomeActivity : BaseActivity<ActivityStockHomeBinding>() {
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var stockDao: StockDetailsDao
    private var stockList : List<StockDetails> = listOf()
    private lateinit var adapter: StockListAdapter
    var mMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stockDao = (application as Application).database.StockDetailsDao()
        preferenceManager = PreferenceManager(this)
        preferenceManager.setUserLoggedIn(true)
        initUI()
        getTodaysStockDataApiCall()

        adapter = StockListAdapter(listOf()) {
            saveStockToDb(it)
        }
        binding.recyclerView.adapter = adapter
    }

    private fun getTodaysStockDataApiCall() {
        val call = RetrofitClient.instance.getStockData(
            "Bearer $API_KEY",
            Calendar.getInstance().previousDate().toString()
        )
        call.enqueue(object : Callback<StockPayload> {
            override fun onResponse(call: Call<StockPayload>, response: Response<StockPayload>) {
                if (response.isSuccessful) {
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.progressCircular.visibility = View.GONE
                    val data = response.body()
                    // Process the data
                    stockList = data?.results ?: listOf()
                    adapter.submitList(stockList)
                    Log.d("StockHome", "onResponse: ${stockList.size}")
                } else {
                    // Handle error
                    Toast.makeText(
                        this@StockHomeActivity,
                        "Error: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<StockPayload>, t: Throwable) {
                Log.d("StockHome", "onFailure: ${t.localizedMessage}")
                Log.d("StockHome", "onFailure: ${t.message}")
                Log.d("StockHome", "onFailure: ${t.cause}")
                // Handle failure
                Toast.makeText(
                    this@StockHomeActivity,
                    "Failed to fetch data: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun createBinding(): ActivityStockHomeBinding {
        return ActivityStockHomeBinding.inflate(LayoutInflater.from(this))
    }


    fun initUI(){
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.visibility= View.GONE
        binding.progressCircular.visibility = View.VISIBLE
        binding.topAppBar.setOnMenuItemClickListener{
            when (it.itemId) {
                R.id.search -> {
                    // Handle search action
                    true
                }
                R.id.detailsPage -> {
                    val intent = Intent(this@StockHomeActivity, BoughtStockListActivity::class.java)
                    startActivity(intent)
                    true
                }R.id.logout -> {
                    // Handle settings action
                    true
                }
                // Add cases for other menu items as needed
                else -> false
            }
        }

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                search(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

    }


    fun saveStockToDb(stockDetails: StockDetails) {
        CoroutineScope(Dispatchers.IO).launch {
            stockDao.buyStock(stockDetails)
        }
    }

    fun logout(){
        val sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        // Clear Room Database
        //(application as Application).database.clearAllTables()
    }

    private fun search(query: String) {
        val filteredList = stockList.filter {
            it.symbol.startsWith(query)
        }
        updateRecyclerView(filteredList)
    }

    private fun updateRecyclerView(newList: List<StockDetails>) {
        adapter.submitList(newList)
        adapter.notifyDataSetChanged()
    }

}