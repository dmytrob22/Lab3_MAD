package com.app.recyclerviewmvvm.repository.network

import com.app.recyclerviewmvvm.model.MoviesResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MyAPI {

    @GET("?apikey=899f27bf")
    suspend fun getMovies(
        @Query("s") search: String // The search query parameter
    ): Response<MoviesResponse>

    companion object {
        // Factory method to create an instance of MyAPI
        operator fun invoke(): MyAPI {
            return Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create()) // Converts JSON to Kotlin objects
                .build()
                .create(MyAPI::class.java) // Creates the Retrofit implementation of MyAPI
        }
    }
}
