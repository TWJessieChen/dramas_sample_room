package com.jc666.dramas_sample_room

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jc666.dramas_sample_room.application.MainApplication
import com.jc666.dramas_sample_room.database.model.DramaData
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 *
 * 主頁面(MVVM架構設計)
 * 使用SharedPreferences來儲存query key word
 *
 * @author JC666
 */

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName

    private val WORD = "WORD"

    private lateinit var wifiManager: WifiManager

    private var isWifiEnabled : Boolean = false

    private var adapter : MainRecyclerViewAdapter? = null

    private var recyclerView : RecyclerView? = null

    private var editor: SharedPreferences.Editor? = null

    private val viewModel by lazy {
        MainViewModel(MainApplication.repository!!)
    }

    private val scope = MainScope()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val searchView = menu!!.findItem(R.id.search).actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {

                viewModel.searchText(newText!!)

                editor!!.putString(WORD, newText).apply()

                return true
            }
        })

        searchView.setQuery(MainApplication.pref!!.getString(WORD, ""), false)

        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view)
//        searchView = findViewById(R.id.searchView)
        recyclerView!!.setHasFixedSize(true)
        adapter = MainRecyclerViewAdapter(object : MainRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(item: DramaData) {
                Log.d(TAG,"onItemClick: " + item.name)
                val intent = Intent(MainApplication.appContext, DetailInfoActivity::class.java)
                val extras = Bundle()
                extras.putInt("EXTRA_DRAMA_ID", item.drama_id!!)
                intent.putExtra("Bundle",extras)
                startActivity(intent)
            }
        })
        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = LinearLayoutManager(MainApplication.appContext!!)

        editor = MainApplication.pref!!.edit()

        viewModel.allDramaDataListObserver.observe(this) { it ->

            Log.d(TAG,"allDramaDataListObserver: " + it!!.size)
            // Update the cached copy of the words in the adapter.
            it.let {
                adapter!!.submitList(it)
            }

            if(!isWifiEnabled) {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle(R.string.warring)
                    .setMessage(R.string.intent_settings_app)
                    .setPositiveButton(R.string.ok) { _, _ ->
                        val dialogIntent = Intent(Settings.ACTION_SETTINGS)
                        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(dialogIntent)
                    }
                    .setNeutralButton(R.string.cancel) { _, _ ->
                    }
                    .show()
            }

        }

        viewModel.dramaDataList.observe(this) { it ->

            Log.d(TAG,"dramaDataList: " + it!!.size)
            // Update the cached copy of the words in the adapter.
            it.let {
                adapter!!.submitList(it)
            }

//            if(!isWifiEnabled) {
//                AlertDialog.Builder(this@MainActivity)
//                    .setTitle(R.string.warring)
//                    .setMessage(R.string.intent_settings_app)
//                    .setPositiveButton(R.string.ok) { _, _ ->
//                        val dialogIntent = Intent(Settings.ACTION_SETTINGS)
//                        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                        startActivity(dialogIntent)
//                    }
//                    .setNeutralButton(R.string.cancel) { _, _ ->
//                    }
//                    .show()
//            }

        }

        val filterWord = MainApplication.pref!!.getString(WORD, "")
        if(!filterWord.equals("")) {
//            viewModel.init()
        }

        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    }

    override fun onStart() {
        super.onStart()

        // Enable Wi-Fi
        if (wifiManager.isWifiEnabled) {
            Toast.makeText(this, R.string.prompt_enabling_wifi, Toast.LENGTH_SHORT).show()
            isWifiEnabled = true
        } else {
            Toast.makeText(this, R.string.prompt_disabling_wifi, Toast.LENGTH_SHORT).show()
            isWifiEnabled = false
        }

    }

    override fun onResume() {
        super.onResume()
        scope.launch {

            if(isWifiEnabled) {
                val tasks = viewModel.httpDramasDataRequest()
            }

        }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

}