/*
 * Copyright (c) 2021.
 * Created by Ahmed Awad
 * ahmed.mmawad@hotmail.com
 */

package com.cashu.moviephotos.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageUtils {

    companion object {
        fun setImageView(context: Context, imageUrl: String, imageView: ImageView) {
            Glide
                .with(context)
                .load(imageUrl)
                .into(imageView)
        }
    }
}
