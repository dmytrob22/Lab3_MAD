package com.app.recyclerviewmvvm.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.app.recyclerviewmvvm.repository.database.MovieDatabase
import com.app.recyclerviewmvvm.repository.network.MovieNetworkDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Ensures dependencies are tied to the application's lifecycle
object AppModule {

    // Provides a singleton instance of MovieDatabase
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context // Injects the application context
    ): MovieDatabase {
        return MovieDatabase.getInstance(context) // Retrieves the singleton database instance
    }

    // Provides a singleton instance of MovieNetworkDataSource
    @Singleton
    @Provides
    fun provideMovieNetworkDataSource(): MovieNetworkDataSource {
        return MovieNetworkDataSource() // Constructs the network data source
    }
}
