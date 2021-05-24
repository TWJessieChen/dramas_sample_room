package com.jc666.dramas_sample_room.http

import android.util.Log
import com.google.gson.GsonBuilder
import com.jc666.dramas_sample_room.data.DramaList
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import ru.gildor.coroutines.okhttp.await

/**
 *
 * 使用okhttp3 sdk 跟 gildor/kotlin-coroutines-okhttp sdk，一起搭配使用
 * 可以達到Kotlin coroutines await extension for OkHttp功效
 *
 * @author JC666
 */


object HttpGetMethod {
    private val TAG = HttpGetMethod::class.java.simpleName

    suspend fun getDramasDataRequest() : Triple<Boolean, String, DramaList> {

        var resultList: DramaList? = null

        val client = OkHttpClient()

        val req = Request.Builder().url(DramaContact.BASE_URL).build()

        try {
            val resStr = client.newCall(req).await().body!!.string()
            val gson = GsonBuilder().create()
            resultList = gson.fromJson(resStr, DramaList::class.java)
        } catch (e: IOException) {
            // Catch original exception
            // Use some logger that will write line number to logs, so you can find the source of exception
            // or just wrap exception and throw it to get stacktrace
//            throw IOException("Some additional debug info: ${e.message}", e)
            return Triple(false, e.message.toString(), resultList!!)
        }

        Log.d(TAG, "" + resultList!!.data.size)
        return Triple(true, "", resultList!!)
    }

}