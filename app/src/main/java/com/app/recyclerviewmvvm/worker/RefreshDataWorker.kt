package com.app.recyclerviewmvvm.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.app.recyclerviewmvvm.repository.MovieRepository
import com.app.recyclerviewmvvm.repository.database.MovieDatabase
import retrofit2.HttpException

class RefreshDataWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        // Initialize database and repository
        val database = MovieDatabase.getInstance(applicationContext)
        val repository = MovieRepository(database)

        return try {
            // Refresh movie data for the "Lord of the Rings" franchise
            repository.refreshFranchiseMovies("Lord of the Rings")
            Result.success()
        } catch (exception: HttpException) {
            // Retry on network errors
            Result.retry()
        }
    }
}
