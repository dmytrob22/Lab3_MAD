package com.app.recyclerviewmvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import com.app.recyclerviewmvvm.R
import com.app.recyclerviewmvvm.adapter.MovieAdapter
import com.app.recyclerviewmvvm.adapter.MovieListener
import com.app.recyclerviewmvvm.databinding.FragmentMainBinding
import com.app.recyclerviewmvvm.viewmodel.MovieViewModel

@AndroidEntryPoint
class MainFragment : Fragment() {

    // Initialize viewModel on fragment creation
    private val movieViewModel: MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment and set up data binding
        val binding: FragmentMainBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main,
            container,
            false
        )

        // Initialize the MovieAdapter and pass the listener
        val movieAdapter = MovieAdapter(MovieListener { movie ->
            movieViewModel.onMovieClicked(movie)
        })

        // Set adapter for the RecyclerView
        binding.movieRecyclerview.adapter = movieAdapter

        // Bind the viewModel to the layout
        binding.viewmodel = movieViewModel

        // Observe navigation events and navigate to the movie detail screen
        movieViewModel.navigateToMovieDetail.observe(viewLifecycleOwner) { movie ->
            movie?.let {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToDetailFragment(movie))
                movieViewModel.onMovieDetailNavigated() // Reset the navigation event
            }
        }

        // Observe the movie list and update the adapter
        movieViewModel.movies.observe(viewLifecycleOwner) {
            movieAdapter.differ.submitList(it)
            binding.groupLoading.visibility = View.GONE
        }

        // Observe dark mode preference and update the theme accordingly
        movieViewModel.darkMode.observe(viewLifecycleOwner) { isDarkMode ->
            setDefaultNightMode(if (isDarkMode) MODE_NIGHT_YES else MODE_NIGHT_NO)
        }

        return binding.root
    }
}
