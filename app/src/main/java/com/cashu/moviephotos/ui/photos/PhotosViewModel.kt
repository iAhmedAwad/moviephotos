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
    val photosRepo = PhotosMainRepository()
    private  val TAG = "PhotosViewModel"
    private val _errorState = MutableLiveData<String>()
    val errorState: LiveData<String>
        get() = _errorState

    private val _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>>
        get() = _photos

    init {
        getData()
    }

    fun getData() {

        CoroutineScope(IO).launch {
            Log.d(TAG, "getData: getting data .. viewmodel")
            try {
                val result = photosRepo.getDataAsync()
                Log.d(TAG, "getData: getting data .. viewmodel")

                when (result) {
                    is Deferred<*> -> {
                        Log.d(TAG, "getData: when Deferred")

                        val res =result.await()
                        if(res is PhotoWrapper){
                        _photos.postValue(res.photos.photo)
                        }
                    }

                    is List<*> -> {
                        if (result.isNotEmpty()) {
                            Log.d(TAG, "getData: when result.isNotEmpty")

                            if (result[0] is Photo) {
                                _photos.postValue(result as List<Photo>)
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
