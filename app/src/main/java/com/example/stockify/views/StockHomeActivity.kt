package com.example.stockify.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
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
import com.example.stockify.database.dao.UserDao
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


class StockHomeActivity : BaseActivity<ActivityStockHomeBinding>() {
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var stockDao: StockDetailsDao
    private var stockList : List<StockDetails> = listOf()

    private lateinit var adapter: StockListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stockDao = (application as Application).database.StockDetailsDao()
        preferenceManager = PreferenceManager(this)
        preferenceManager.setUserLoggedIn(true)
        binding.recyclerView.visibility= View.GONE
        binding.progressCircular.visibility = View.VISIBLE

        initUI()

        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        val call = RetrofitClient.instance.getStockData("Bearer $API_KEY", Calendar.getInstance().previousDate().toString())
        call.enqueue(object : Callback<StockPayload> {
            override fun onResponse(call: Call<StockPayload>, response: Response<StockPayload>) {
                if (response.isSuccessful) {
                    binding.recyclerView.visibility= View.VISIBLE
                    binding.progressCircular.visibility = View.GONE
                    val data = response.body()
                    // Process the data
                    stockList = data?.results ?: listOf()
                    adapter.submitList(stockList)
                    Log.d("StockHome", "onResponse: ${stockList.size}")
                } else {
                    // Handle error
                    Toast.makeText(this@StockHomeActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<StockPayload>, t: Throwable) {
                Log.d("StockHome", "onFailure: ${t.localizedMessage}")
                Log.d("StockHome", "onFailure: ${t.message}")
                Log.d("StockHome", "onFailure: ${t.cause}")
                // Handle failure
                Toast.makeText(this@StockHomeActivity, "Failed to fetch data: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
        adapter = StockListAdapter(listOf()) {
            saveStokeToDb(it)
        }
        binding.recyclerView.adapter = adapter
    }

    override fun createBinding(): ActivityStockHomeBinding {
        return ActivityStockHomeBinding.inflate(LayoutInflater.from(this))
    }


    fun initUI(){
        binding.extendedFloatingActionButton.setOnClickListener {
            val intent = Intent(this@StockHomeActivity, BoughtStockListActivity::class.java)
            startActivity(intent)

        }

    }

    fun saveStokeToDb(stockDetails: StockDetails) {
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

}