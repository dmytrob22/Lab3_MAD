package com.app.recyclerviewmvvm.repository.network

import com.app.recyclerviewmvvm.model.MoviesResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MyAPI {

    @GET("http://www.omdbapi.com/?apikey=899f27bf")
    suspend fun getMovies(
        @Query("s") search: String
    ): Response<MoviesResponse>

    companion object {
        operator fun invoke(): MyAPI {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://www.omdbapi.com/")
                .build()
                .create(MyAPI::class.java)
        }
    }
}