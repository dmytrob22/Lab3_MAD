package com.app.recyclerviewmvvm.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.recyclerviewmvvm.model.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract val movieDatabaseDao: MovieDatabaseDao

    companion object {
        // Volatile ensures visibility of changes to INSTANCE across threads
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        // Returns the singleton instance of the database
        fun getInstance(context: Context): MovieDatabase {
            // Synchronize to ensure only one instance is created
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MovieDatabase::class.java,
                        "movies_database"
                    )
                        .fallbackToDestructiveMigration() // Handles schema changes by wiping data
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
