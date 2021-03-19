/*
 * Copyright (c) 2021.
 * Created by Ahmed Awad
 * ahmed.mmawad@hotmail.com
 */

package com.cashu.moviephotos.data.repos.remote

import com.cashu.moviephotos.data.model.PhotoWrapper
import com.cashu.moviephotos.data.remote.APIServices
import com.cashu.moviephotos.data.remote.RetrofitClient
import com.cashu.moviephotos.data.remote.constants.APIQueries
import kotlinx.coroutines.Deferred

class PhotosRemoteRepository() {

    private val retrofitClient = RetrofitClient.getRetrofitClient()
    private var apiServices = retrofitClient?.create(APIServices::class.java)

    suspend fun getDataAsync(
        method: String, format: String,
        text: String, page: Int,
        perPage: Int, jsonCallback: Int = 50, apiKey: String = APIQueries.API_KEY_VALUE
    ): Deferred<PhotoWrapper>? {

        return apiServices?.getDataAsync(method, format, text, page, perPage, jsonCallback, apiKey)
    }


}
