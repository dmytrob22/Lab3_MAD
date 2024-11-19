package com.app.recyclerviewmvvm.repository

import com.app.recyclerviewmvvm.repository.database.MovieDatabase
import com.app.recyclerviewmvvm.repository.network.MovieNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepository
@Inject constructor(
    private val movieDatabase: MovieDatabase
) {

    val movies = movieDatabase.movieDatabaseDao.getAllMovies()

    suspend fun refreshFranchiseMovies(franchiseName: String) {
        withContext(Dispatchers.IO) {
            val movies = async {
                MovieNetworkDataSource().fetchFranchiseMovies(franchiseName)
            }
            movies.await()?.let {
                movieDatabase.movieDatabaseDao.clear()
                movieDatabase.movieDatabaseDao.insert(it)
            }
        }
    }
}