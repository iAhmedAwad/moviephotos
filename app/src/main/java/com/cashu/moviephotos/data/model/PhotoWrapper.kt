package com.cashu.moviephotos.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotoWrapper(
    var photos: Photos = Photos(),
    var stat: String = ""
)
