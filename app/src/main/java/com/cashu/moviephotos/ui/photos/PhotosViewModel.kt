/*
 * Copyright (c) 2021.
 * Created by Ahmed Awad
 * ahmed.mmawad@hotmail.com
 */

package com.cashu.moviephotos.ui.photos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cashu.moviephotos.data.model.Photo
import com.cashu.moviephotos.data.model.PhotoWrapper
import com.cashu.moviephotos.data.repos.PhotosMainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class PhotosViewModel : ViewModel() {

    private var photosList = ArrayList<Any>()
    var page = 0
        get() = field
        private set
    val photosRepo = PhotosMainRepository()
    private val TAG = "PhotosViewModelTag"
    private val _errorState = MutableLiveData<String>()
    val errorState: LiveData<String>
        get() = _errorState

    private val _photos = MutableLiveData<List<Any>>()
    val photos: LiveData<List<Any>>
        get() = _photos

    private val _localPhotos = MutableLiveData<List<Any>>()
    val localPhotos: LiveData<List<Any>>
        get() = _localPhotos

    private val _pageCount = MutableLiveData<Int>()
    val pageCount: LiveData<Int>
        get() = _pageCount

    init {
        getData()
    }

    fun getData() {

        CoroutineScope(IO).launch {
            Log.d(TAG, "getData: getting data .. viewmodel")
            try {
                val result = photosRepo.getDataAsync(++page)
                Log.d(TAG, "getData: getting data .. viewmodel")

                when (result) {
                    is Deferred<*> -> {
                        Log.d(TAG, "getData: when Deferred")

                        val res = result.await()
                        if (res is PhotoWrapper) {
                            //_photos.postValue(res.photos.photo)
                            photosList.addAll(res.photos.photo)
                            _photos.postValue(photosList)
                            _pageCount.postValue(res.photos.pages)
                            if (res.photos.page == 1) {
                                photosRepo.addToLocalDatabase(res.photos.photo)
                            }
                        }
                    }

                    is List<*> -> {
                        if (result.isNotEmpty()) {
                            Log.d(TAG, "getData: when result.isNotEmpty")

                            if (result[0] is Photo) {
                                _localPhotos.postValue(result as List<Photo>)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "getData: Exception: ${e.message}")
                _errorState.postValue(e.message)
            }
        }
    }
}
