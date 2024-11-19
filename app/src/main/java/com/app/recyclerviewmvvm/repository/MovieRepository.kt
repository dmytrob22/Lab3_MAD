package com.app.recyclerviewmvvm.repository

import com.app.recyclerviewmvvm.repository.database.MovieDatabase
import com.app.recyclerviewmvvm.repository.network.MovieNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieDatabase: MovieDatabase // Injected MovieDatabase instance
) {

    val movies = movieDatabase.movieDatabaseDao.getAllMovies()

    suspend fun refreshFranchiseMovies(franchiseName: String) {
        // Switches to IO thread for network operations
        withContext(Dispatchers.IO) {
            val moviesFromNetwork = async {
                MovieNetworkDataSource().fetchFranchiseMovies(franchiseName)
            }

            moviesFromNetwork.await()?.let { newMovies ->
                movieDatabase.movieDatabaseDao.clear()
                movieDatabase.movieDatabaseDao.insert(newMovies)
            }
        }
    }
}
