package com.app.recyclerviewmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.app.recyclerviewmvvm.model.Movie
import com.app.recyclerviewmvvm.repository.MovieRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val movies: LiveData<List<Movie>> = movieRepository.movies

    private val _darkMode = MutableLiveData<Boolean>(false)
    val darkMode: LiveData<Boolean>
        get() = _darkMode

    private val _navigateToMovieDetail = MutableLiveData<Movie?>()
    val navigateToMovieDetail: LiveData<Movie?>
        get() = _navigateToMovieDetail

    init {
        // Fetch movies for "Lord of the Rings" on ViewModel initialization
        viewModelScope.launch {
            movieRepository.refreshFranchiseMovies("Lord of the Rings")
        }
    }

    // Toggle dark mode value
    fun darkModeChange() {
        _darkMode.value = _darkMode.value != true
    }

    // Set the selected movie for navigation
    fun onMovieClicked(movie: Movie) {
        _navigateToMovieDetail.value = movie
    }

    // Reset the navigation event after it's handled
    fun onMovieDetailNavigated() {
        _navigateToMovieDetail.value = null
    }
}
