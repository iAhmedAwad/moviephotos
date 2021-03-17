package com.cashu.moviephotos.data.remote

import com.cashu.moviephotos.data.model.PhotoWrapper
import com.cashu.moviephotos.data.remote.constants.APIQueries
import retrofit2.Response

class APIRepository(private val apiServices: APIServices) {

    suspend fun getPhotos(
        method: String, format: String,
        text: String, page: Int,
        perPage: Int, jsonCallback: Int = 50, apiKey: String = APIQueries.API_KEY_VALUE
    ): Response<PhotoWrapper> {
       // println("weewaa ${RetrofitClient.BASE_URL}$method$format$text$page$perPage$apiKey")
        return apiServices.getPhotos(method, format, text, page, perPage, jsonCallback, apiKey)
    }


}
