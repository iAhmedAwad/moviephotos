package com.cashu.moviephotos.data.remote


import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitClient {


    companion object {
        private var retrofitInstance: Retrofit? = null

        //private const val BASE_URL = "http://46.151.209.48:8040/" // live
         const val BASE_URL = "https://www.flickr.com/"
//        private const val BASE_URL = "http://192.168.1.10:8050/" // local


        fun getRetrofitClient(): Retrofit? {
            if (retrofitInstance == null) {

                val interceptor = HttpLoggingInterceptor()
                interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }

                val okHttpClient = OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .build()

              val moshi=  Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()

                retrofitInstance = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .addCallAdapterFactory(NetworkResponseAdapterFactory())
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .client(okHttpClient)
                    .build()

            }
            return retrofitInstance
        }
    }
    
}
