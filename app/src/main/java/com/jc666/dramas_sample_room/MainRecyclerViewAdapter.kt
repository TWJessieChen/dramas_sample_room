package com.jc666.dramas_sample_room

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

import androidx.recyclerview.widget.RecyclerView;
import com.jc666.dramas_sample_room.database.model.DramaData
import com.jc666.dramas_sample_room.utils.BitmapAndBase64StringToolUtil


/**
 *
 *
 * @author JC666
 */

class MainRecyclerViewAdapter(private var listener: OnItemClickListener) : ListAdapter<DramaData, MainRecyclerViewAdapter.DramaDataViewHolder>(WORDS_COMPARATOR) {
    private val TAG = MainRecyclerViewAdapter::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DramaDataViewHolder {
        return DramaDataViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: DramaDataViewHolder, position: Int) {
        val current = getItem(position)

        holder.iv_thumb.setImageBitmap(BitmapAndBase64StringToolUtil.convertStringToBitmap(current.base64Thumb!!))

        holder.container.setOnClickListener{
            listener.onItemClick(current)
        }

        holder.tv_name.text = current.name

        holder.tv_createdAt.text = current.created_at

        holder.tv_rating.text = current.rating

    }

    class DramaDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val container : LinearLayout = itemView.findViewById(R.id.container)
        val iv_thumb: ImageView = itemView.findViewById(R.id.iv_thumb)
        val tv_name: TextView = itemView.findViewById(R.id.tv_name)
        val tv_createdAt: TextView = itemView.findViewById(R.id.tv_createdAt)
        val tv_rating: TextView = itemView.findViewById(R.id.tv_rating)

        companion object {
            fun create(parent: ViewGroup): DramaDataViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item, parent, false)
                return DramaDataViewHolder(view)
            }
        }
    }

    companion object {
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<DramaData>() {
            override fun areItemsTheSame(oldItem: DramaData, newItem: DramaData): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: DramaData, newItem: DramaData): Boolean {
                return oldItem.drama_id == newItem.drama_id
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: DramaData)
    }
}
