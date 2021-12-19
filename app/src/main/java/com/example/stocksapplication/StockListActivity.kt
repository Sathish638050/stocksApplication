package com.example.stocksapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stocksapplication.Adapter.StackViewAdapter
import com.example.stocksapplication.model.AllStocks
import com.example.stocksapplication.model.Stocks
import com.example.stocksapplication.model.User
import com.example.stocksapplication.network.AppInstance
import com.example.stocksapplication.network.AppService
import com.example.stocksapplication.utils.LoadingItem
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class StockListActivity : AppCompatActivity(){
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var toggle: ActionBarDrawerToggle

    companion object{
        const val INTENT_PARCELABLE = "OBJECT_INTENT"
    }

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_list)

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.action_bar_layout)

        sharedPreferences = getSharedPreferences("user",Context.MODE_PRIVATE)

        val token = sharedPreferences.getString("token","")
        val email = sharedPreferences.getString("email","")
        val userObj = intent.getParcelableExtra<User>(LoginActivity.INTENT_PARCELABLE2)
        val loading =   LoadingItem(this)

        if(token != null){

            CoroutineScope(Dispatchers.IO).launch {
                val instance = AppInstance.getAppInstance().create(AppService::class.java)
                instance.getStocks("Bearer $token").enqueue(object :Callback<AllStocks>{
                    override fun onResponse(call: Call<AllStocks>, response: Response<AllStocks>) {
                        if(response.isSuccessful){
                            //Toast.makeText(applicationContext,response.isSuccessful.toString(),Toast.LENGTH_SHORT).show()
                            val recycle : RecyclerView = findViewById(R.id.recyclerView)
                            val adapter = StackViewAdapter(response.body()!!.stocks)
                            recycle.adapter = adapter
                            recycle.layoutManager = LinearLayoutManager(this@StockListActivity)
                            adapter.setOnItemClickListener(object : StackViewAdapter.onItemClickListener{
                                override fun onItemClick(position: Int) {

                                    val intent = Intent(this@StockListActivity,GetStocksActivity::class.java)
                                    intent.putExtra(INTENT_PARCELABLE,response.body()!!.stocks[position])
                                    startActivity(intent)
                                }

                            })

                        }
                    }

                    override fun onFailure(call: Call<AllStocks>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
            }
        }
        val drawerllayout : DrawerLayout =findViewById(R.id.drawerLayout)
        val navView: NavigationView =findViewById(R.id.navview)


        toggle= ActionBarDrawerToggle(this,drawerllayout,R.string.open,R.string.close)
        drawerllayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.appicon)




        navView.setNavigationItemSelectedListener {
            when(it.itemId){

                R.id.profile -> startActivity(Intent(this@StockListActivity,ProfileActivity::class.java).putExtra(
                    INTENT_PARCELABLE,userObj))
                R.id.otherUsers -> startActivity(Intent(this@StockListActivity,OtherUserActivity::class.java))
                R.id.myStock -> startActivity(Intent(this@StockListActivity,MyStocksActivity::class.java))
            }
            true
        }




    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)

    }




}