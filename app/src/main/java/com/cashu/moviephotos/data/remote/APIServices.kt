package com.cashu.moviephotos.data.remote

import com.cashu.moviephotos.data.model.PhotoWrapper
import com.cashu.moviephotos.data.remote.constants.APIPaths
import com.cashu.moviephotos.data.remote.constants.APIQueries
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface APIServices {

    @GET(APIPaths.SERVICES_REST)
    fun getDataAsync(
        @Query(APIQueries.METHOD) method: String,
        @Query(APIQueries.FORMAT) format: String,
        @Query(APIQueries.TEXT) text: String,
        @Query(APIQueries.PAGE) page: Int,
        @Query(APIQueries.PER_PAGE) perPage: Int,
        @Query(APIQueries.JSON_CALLBACK) jsonCallback: Int,
        @Query(APIQueries.API_KEY) apiKey: String
    ): Deferred<PhotoWrapper>

}
