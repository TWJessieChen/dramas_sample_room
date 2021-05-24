package com.jc666.dramas_sample_room

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.jc666.dramas_sample_room.application.MainApplication
import com.jc666.dramas_sample_room.database.DataRepository
import com.jc666.dramas_sample_room.database.model.DramaData
import com.jc666.dramas_sample_room.http.HttpGetMethod
import com.jc666.dramas_sample_room.utils.BitmapAndBase64StringToolUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


/**
 *
 * ViewModel架構
 * 使用LiveData
 * 使用CoroutineScope
 *
 * @author JC666
 */

class MainViewModel(private val repository: DataRepository)  : ViewModel() {
    private val TAG = MainViewModel::class.java.simpleName

    private val viewModelJob = SupervisorJob()

    val allDramaDataListObserver: LiveData<List<DramaData>> = repository.allDramaDataListObserver.asLiveData()

    var dramaDataList = MutableLiveData<List<DramaData>>()

    val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    init {
        viewModelScope.launch {

//            dramaDataList = repository.getDramaDataList()

        }
    }

    fun init() {
        ioScope.launch {
            dramaDataList.postValue(repository.getDramaDataList())
        }
    }

    fun searchText(filterStr: String) {
        ioScope.launch {
            dramaDataList.postValue(repository.getFilterDramaDataList(filterStr))
        }
    }

    suspend fun httpDramasDataRequest() {
        viewModelScope.launch {
            val resultList = HttpGetMethod.getDramasDataRequest()

            if(resultList.first) {
                Log.d(TAG, "" + resultList.third.data.size)

                ioScope.launch {

                    repository.insert(resultList.third)

                    for(data in resultList.third.data) {
                        try {

                            val bitmap: Bitmap = Glide
                                .with(MainApplication.appContext!!)
                                .asBitmap()
                                .load(data.thumb)
                                .submit()
                                .get()

                            Log.d(TAG,"width: " + bitmap.width + " height: " + bitmap.height)

                            val base64Str = BitmapAndBase64StringToolUtil.convertBitmapToBase64String(bitmap)

                            repository.updateBitmap(data, base64Str!!)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }



            } else {
                Log.d(TAG, "Error: " + resultList.second)
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

class DataViewModelFactory(private val repository: DataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}