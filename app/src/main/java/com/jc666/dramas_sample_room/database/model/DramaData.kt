package com.jc666.dramas_sample_room.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
 */

@Entity(tableName = "drama_table")
data class DramaData(
    @PrimaryKey @ColumnInfo(name = "drama_id") val drama_id: Int? = 0,
    @ColumnInfo(name = "created_at") val created_at: String?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "rating") val rating: String?,
    @ColumnInfo(name = "thumb") val thumb: String?,
    @ColumnInfo(name = "total_views") val total_views: Int?,
    @ColumnInfo(name = "base64Thumb") val base64Thumb: String?
)
