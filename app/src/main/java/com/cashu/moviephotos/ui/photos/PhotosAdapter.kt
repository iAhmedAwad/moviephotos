package com.cashu.moviephotos.ui.photos

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cashu.moviephotos.R
import com.cashu.moviephotos.data.model.Photo
import com.cashu.moviephotos.data.remote.constants.APIQueries
import com.cashu.moviephotos.utils.ImageUtils


class PhotosAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var photosList = ArrayList<Any>()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageViewItemPhoto: ImageView = itemView.findViewById(R.id.imageView_photo_item)

        fun bindData(photo: Photo) {
            // val photo = photosList[position]
            val imageUrl = APIQueries.FARM + photo.farm +
                    APIQueries.DOMAIN + photo.server + APIQueries.SLASH +
                    photo.id + APIQueries.UNDERSCORE +
                    photo.secret + APIQueries.EXTENSION

           ImageUtils.setImageView(itemView.context, imageUrl, imageViewItemPhoto)
        }

    }

    inner class MyLoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun getItemViewType(position: Int): Int {
        return when (photosList[position]) {
            is Photo -> R.layout.item_photo
            else -> R.layout.item_loading
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_photo -> MyViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_photo, parent, false)
            )

            else -> MyLoadingViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_loading, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is MyViewHolder) {
            holder.bindData(photosList[position] as Photo)
        }
    }

    override fun getItemCount(): Int {
        return photosList.size
    }

    fun setData(photoList: List<Photo>) {
        photosList.clear()
        this.photosList = ArrayList(photoList)
        notifyDataSetChanged()
    }

    fun addData(photoList: List<Any>) {
        this.photosList.addAll(photoList)
        notifyDataSetChanged()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun addLoader() {
        Log.d(Companion.TAG, "addLoader: list size before is ${this.photosList.size}")
        if (this.photosList.contains(0))
        // this.photosList.remove(0)
            this.photosList.removeIf { it !is Photo }
        Log.d(Companion.TAG, "addLoader: list size after is ${this.photosList.size}")
        this.photosList.add(0)
        notifyDataSetChanged()
    }

    fun removeLoader() {
        this.photosList.remove(0)
        notifyDataSetChanged()
    }

    companion object {
        private const val TAG = "PhotosAdapter"
    }

}
