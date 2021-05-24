package com.jc666.dramas_sample_room.database.model

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import kotlinx.coroutines.flow.Flow

/**
 * 1.使用Room 搭配 kotlinx-coroutines 的 Flow 觀察room 有變化的話，可以異步同步到 viewmodel 轉換 liveData
 * 2.Room SQL 語法使用 LIKE 模糊查詢方法
 */

@Dao
interface DataDao {

    // The flow always holds/caches latest version of data. Notifies its observers when the
    // data has changed.
    @Query("SELECT * FROM drama_table ORDER BY drama_id ASC")
    fun getDramaDataListObserver(): Flow<List<DramaData>>

    @Query("SELECT * FROM drama_table ORDER BY drama_id ASC")
    fun getDramaDataList() : List<DramaData>

    @Query("SELECT * FROM drama_table where name LIKE '%' || :word || '%' or rating LIKE '%' || :word || '%' or created_at LIKE '%' || :word || '%' ORDER BY drama_id ASC")
    fun getFilterDramaDataList(word: String) : List<DramaData>

    @Insert(onConflict = REPLACE)
    suspend fun insert(dramaData: DramaData)

    @Update(onConflict = REPLACE)
    suspend fun updateBitmap(dramaData : DramaData)

}
