/*
 * Copyright (c) 2021.
 * Created by Ahmed Awad
 * ahmed.mmawad@hotmail.com
 */

package com.cashu.moviephotos.data.model

data class Photos(
    var page: Int = 0,
    var pages: Int = 0,
    var perpage: Int = 0,
    var photo: List<Photo> = listOf(),
    var total: String = ""
)
