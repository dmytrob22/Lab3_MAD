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
) : CoroutineWorker(
    appContext,
    workerParams) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val dataSource = MovieDatabase.getInstance(applicationContext)
        return try {
            val repository = MovieRepository(dataSource)
            repository.refreshFranchiseMovies("Lord of the rings")
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}
