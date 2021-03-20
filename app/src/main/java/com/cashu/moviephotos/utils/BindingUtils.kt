/*
 * Copyright (c) 2021.
 * Created by Ahmed Awad
 * ahmed.mmawad@hotmail.com
 */

package com.cashu.moviephotos.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.cashu.moviephotos.R
import com.cashu.moviephotos.data.remote.constants.APIQueries

@BindingAdapter("bindPhoto:farm", "bindPhoto:server", "bindPhoto:id", "bindPhoto:secret")
fun bindImage(view: ImageView, farm: Int, server: String, id: String, secret: String) {

    val imageUrl = APIQueries.FARM + farm +
            APIQueries.DOMAIN + server + APIQueries.SLASH +
            id + APIQueries.UNDERSCORE +
            secret + APIQueries.EXTENSION

    Glide
        .with(view.context)
        .load(imageUrl)
        .into(view)
}

@BindingAdapter("bindPhoto:imageUrl")
fun bindImage(view: ImageView, imageUrl: String) {

    if(imageUrl.isNotEmpty()){

    Glide
        .with(view.context)
        .load(imageUrl)
        .into(view)
    }else{
        Glide
            .with(view.context)
            .load(R.drawable.banner)
            .into(view)
    }
}

@BindingAdapter("android:src")
fun setImageViewResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}
