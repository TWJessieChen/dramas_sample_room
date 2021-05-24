package com.jc666.dramas_sample_room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import com.jc666.dramas_sample_room.application.MainApplication
import com.jc666.dramas_sample_room.utils.BitmapAndBase64StringToolUtil
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 *
 * 詳細頁面
 *
 * @author JC666
 */

class DetailInfoActivity : AppCompatActivity() {
    private val TAG = DetailInfoActivity::class.java.simpleName

    private val viewModel by lazy {
        MainViewModel(MainApplication.repository!!)
    }

    private val scope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_info)

        val bundle= intent.getBundleExtra("Bundle")
        val dramaId = bundle?.getInt("EXTRA_DRAMA_ID")

        viewModel.dramaDataList.observe(this) { it ->

            Log.d(TAG,"dramaDataList: " + it!!.size)

            scope.launch {

                Log.d(TAG,"Drama id: " + dramaId)

                val iv_thumb: ImageView = findViewById(R.id.iv_thumb)
                val tv_name: TextView = findViewById(R.id.tv_name)
                val tv_createdAt: TextView = findViewById(R.id.tv_createdAt)
                val tv_rating: TextView = findViewById(R.id.tv_rating)
                val total_views: TextView = findViewById(R.id.total_views)

                for(data in it!!) {
                    if(data.drama_id!!.equals(dramaId)) {
                        iv_thumb.setImageBitmap(BitmapAndBase64StringToolUtil.convertStringToBitmap(data.base64Thumb!!))
                        tv_name.text = "名稱: " + data.name
                        tv_createdAt.text = "出版日期: " + data.created_at
                        tv_rating.text = "評分 : " + data.rating
                        total_views.text = "觀看次數: " + data.total_views.toString()
                    }
                }
            }

        }

        viewModel.init()

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
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