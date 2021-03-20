/*
 * Copyright (c) 2021.
 * Created by Ahmed Awad
 * ahmed.mmawad@hotmail.com
 */

package com.cashu.moviephotos.ui.singlephoto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SinglePhotoViewModel : ViewModel() {
    private var _imgSrc = MutableLiveData<Int>()
        set(value) {field = value
        }
    val imgSrc: LiveData<Int>
        get() = _imgSrc

}
