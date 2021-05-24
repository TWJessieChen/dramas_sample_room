package com.jc666.dramas_sample_room.database

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.jc666.dramas_sample_room.MainViewModel
import com.jc666.dramas_sample_room.data.Data
import com.jc666.dramas_sample_room.data.DramaList
import com.jc666.dramas_sample_room.database.model.DataDao
import com.jc666.dramas_sample_room.database.model.DramaData
import kotlinx.coroutines.flow.Flow


/**
 *
 *
 */


class DataRepository(private val dataDao: DataDao) {
    private val TAG = DataRepository::class.java.simpleName

    private var dramaDataList: List<DramaData>? = null

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allDramaDataListObserver: Flow<List<DramaData>> = dataDao.getDramaDataListObserver()

//    init {
//        allDramaDataList = dataDao.getDramaDataList()
//    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getDramaDataList() : List<DramaData> {
        dramaDataList = dataDao.getDramaDataList()
        return dramaDataList!!
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getFilterDramaDataList(word: String) : List<DramaData> {
        dramaDataList = dataDao.getFilterDramaDataList(word)
        return dramaDataList!!
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(dataList : DramaList) {

        var dramaDataList = dataDao.getDramaDataList()

        for(data in dataList.data) {
            var isInsert = true
            for(dramaData in dramaDataList) {
                if(dramaData.drama_id!!.equals(data.dramaId)) {
                    isInsert = false
                }
            }

            if(isInsert) {
                val newDataInfo = DramaData(data.dramaId,
                    data.createdAt,
                    data.name,
                    data.rating.toString(),
                    data.thumb,
                    data.dramaId,
                    "")

                dataDao.insert(newDataInfo)
            }

        }

    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateBitmap(data: Data, base64Str: String) {

        var dramaDataList = dataDao.getDramaDataList()

        for(dramaData in dramaDataList) {
            if(data.dramaId.equals(dramaData.drama_id)) {
                if(dramaData.base64Thumb.equals("")) {
                    val updateDataInfo = DramaData(data.dramaId,
                        data.createdAt,
                        data.name,
                        data.rating.toString(),
                        data.thumb,
                        data.dramaId,
                        base64Str)

                    dataDao.updateBitmap(updateDataInfo)
                }
            }
        }

    }

}
