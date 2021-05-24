package com.jc666.dramas_sample_room.data

import com.google.gson.annotations.SerializedName

/**
 *
 * 使用於Http response parser Json的內容格式
 *
 * @author JC666
 */

data class Data(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("drama_id")
    val dramaId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("thumb")
    val thumb: String,
    @SerializedName("total_views")
    val totalViews: Int
)