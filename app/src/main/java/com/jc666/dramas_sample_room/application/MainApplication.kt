package com.jc666.dramas_sample_room.application

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.jc666.dramas_sample_room.database.DataRepository
import com.jc666.dramas_sample_room.database.DataRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/**
 *
 * Application()初始建置
 *
 * @author JC666
 */

class MainApplication  : Application() {
    private val TAG = MainApplication::class.java.simpleName

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")

        appContentResolver = contentResolver

        appContext = applicationContext

        pref = applicationContext.getSharedPreferences("FILTER_WORD", Context.MODE_PRIVATE)

        database = DataRoomDatabase.getDatabase(appContext!!)

        repository = DataRepository(database!!.dataDao())

    }

    companion object {
        private val TAG = MainApplication::class.java.simpleName

        var appContentResolver: ContentResolver? = null
            private set
        var appContext: Context? = null

        var pref: SharedPreferences? = null

        var database: DataRoomDatabase? = null

        var repository: DataRepository? = null
    }

}