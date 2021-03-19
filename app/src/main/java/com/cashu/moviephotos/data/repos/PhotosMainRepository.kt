package com.cashu.moviephotos.data.repos

import android.util.Log
import com.cashu.moviephotos.application.BaseApplication
import com.cashu.moviephotos.data.model.Photo
import com.cashu.moviephotos.data.remote.constants.APIQueries
import com.cashu.moviephotos.data.repos.local.PhotosLocalRepository
import com.cashu.moviephotos.data.repos.remote.PhotosRemoteRepository
import com.cashu.moviephotos.utils.ConnectivityUtils

class PhotosMainRepository {


    private val TAG = "PhotosMainRepository"
    private var photosRemoteRepository: PhotosRemoteRepository = PhotosRemoteRepository()
    private var photosLocalRepository: PhotosLocalRepository = PhotosLocalRepository()

    suspend fun getDataAsync(page: Int): Any? {
        return if (ConnectivityUtils(BaseApplication.appContext).isNetworkConnected) {
            Log.d(TAG, "getDataAsync: ConnectivityUtils(BaseApplication.appContext)")
            photosRemoteRepository.getDataAsync(
                APIQueries.METHOD_PHOTOS_SEARCH, APIQueries.FORMAT_VALUE, APIQueries.TEXT_VALUE,
                page, APIQueries.MIN_PER_PAGE
            )

        } else {
            Log.d(TAG, "getDataAsync: else photosLocalRepository.getDataAsync")
            photosLocalRepository.getDataAsync()
        }
    }

    suspend fun addToLocalDatabase(photoList: List<Photo>) {
        photosLocalRepository.deleteAllPhotos()
        photosLocalRepository.addPhotos(photoList)
    }
}
