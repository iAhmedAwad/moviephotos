package com.cashu.moviephotos.ui.photos

import android.app.Activity
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cashu.moviephotos.R
import com.cashu.moviephotos.data.model.Photo
import com.cashu.moviephotos.data.remote.constants.APIQueries


class PhotosAdapter : RecyclerView.Adapter<PhotosAdapter.MyViewHolder>() {

    private var photosList = ArrayList<Photo>()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewItemPhoto: ImageView = itemView.findViewById(R.id.imageView_photo_item)
        fun bindData(position: Int) {

            val photo = photosList[position]
            val imageUrl = APIQueries.FARM + photo.farm +
                    APIQueries.DOMAIN + photo.server + APIQueries.SLASH +
                    photo.id + APIQueries.UNDERSCORE + photo.secret + APIQueries.EXTENSION

            //Getting width and height
            val displayMetrics = DisplayMetrics()
            (itemView.context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
            val height = displayMetrics.heightPixels
            val width = displayMetrics.widthPixels

            Glide
                .with(itemView.context)
                .load(imageUrl)
                .override(width, 600)
                .centerCrop()
                .into(imageViewItemPhoto)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_photo, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(position)
    }

    override fun getItemCount(): Int {
       return photosList.size
    }

    fun setData(photoList: List<Photo>) {
        photosList.clear()
        this.photosList = ArrayList(photoList)
        notifyDataSetChanged()
    }

    fun addData(photoList: List<Photo>) {
        this.photosList.addAll(photoList)
    }
}
