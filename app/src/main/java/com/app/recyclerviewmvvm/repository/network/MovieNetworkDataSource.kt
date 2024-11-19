package com.app.recyclerviewmvvm.repository.network

import com.app.recyclerviewmvvm.model.Movie
import com.app.recyclerviewmvvm.model.MoviesResponse
import retrofit2.Response
import java.io.IOException

class MovieNetworkDataSource {

    suspend fun fetchFranchiseMovies(franchiseName: String): List<Movie>? {
        var response: Response<MoviesResponse>? = null
        var movies: List<Movie>? = null

        try {
            response = MyAPI().getMovies(franchiseName)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        response?.let {
            if (it.isSuccessful) {
                movies = it.body()?.movieList
            }
        }

        return movies
    }
}
