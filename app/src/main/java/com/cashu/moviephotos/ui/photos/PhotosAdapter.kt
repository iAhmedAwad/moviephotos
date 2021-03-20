/*
 * Copyright (c) 2021.
 * Created by Ahmed Awad
 * ahmed.mmawad@hotmail.com
 */

package com.cashu.moviephotos.ui.photos

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cashu.moviephotos.R
import com.cashu.moviephotos.data.model.Photo
import com.cashu.moviephotos.databinding.ItemPhotoBinding


class PhotosAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var itemClickedCallBack = { _: Photo? -> Unit }
    private var photosList = ArrayList<Any>()

    inner class MyViewHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(photo: Photo) {
            binding.apply {
                this.photo = photo
                executePendingBindings()
            }



            itemView.setOnClickListener {
                itemClickedCallBack.invoke(photo)
            }
        }

    }

    inner class MyLoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class AdvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData() {

            itemView.setOnClickListener {
                itemClickedCallBack.invoke(null)
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return when (photosList[position]) {
            is Photo -> R.layout.item_photo
            is Int -> R.layout.item_loading
            else -> R.layout.item_loading
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_photo -> {
                val binding = DataBindingUtil.inflate<ItemPhotoBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_photo, parent, false
                )
                MyViewHolder(
                    binding
                )
            }

            R.layout.item_loading -> AdvViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_advertisement, parent, false)
            )

            else ->
                MyLoadingViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_loading, parent, false)
                )

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is MyViewHolder) {
            holder.bindData(photosList[position] as Photo)
        }
        if(holder is AdvViewHolder){
            holder.bindData()
        }
    }

    override fun getItemCount(): Int {
        return photosList.size
    }

    fun setData(photoList: List<Any>) {
        photosList.clear()
        this.photosList = ArrayList(photoList)
        notifyItemRangeInserted(0, photoList.size - 1)
        //notifyDataSetChanged()
    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun addLoader() {
        Log.d(TAG, "addLoader: list size before is ${this.photosList.size}")
        if (this.photosList.contains(0))
        // this.photosList.remove(0)
            this.photosList.removeIf { it !is Photo }
        Log.d(TAG, "addLoader: list size after is ${this.photosList.size}")
        this.photosList.add(0)
        notifyDataSetChanged()
    }

    fun removeLoader() {
        this.photosList.remove(0)
        notifyDataSetChanged()
    }

    companion object {
        private const val TAG = "PhotosAdapterTag"
    }

}
