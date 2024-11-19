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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        val binding: FragmentMainBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_main,
                container,
                false)

        val movieViewModel: MovieViewModel by viewModels()

        val movieAdapter = MovieAdapter(
            MovieListener {
                movie -> movieViewModel.onMovieClicked(movie)
            }
        )

        binding.movieRecyclerview.adapter = movieAdapter
        binding.viewmodel = movieViewModel

        movieViewModel.navigateToMovieDetail.observe(viewLifecycleOwner) {
            movie -> movie?.let {
                this.findNavController().navigate(
                    MainFragmentDirections
                        .actionMainFragmentToDetailFragment(movie)
                )
                movieViewModel.onMovieDetailNavigated()
            }
        }

        movieViewModel.movies.observe(viewLifecycleOwner) {
            movieAdapter.differ.submitList(it)
            binding.groupLoading.visibility = View.GONE
        }

        movieViewModel.darkMode.observe(viewLifecycleOwner) {
            setDefaultNightMode(
                if (it)
                    MODE_NIGHT_YES
                else
                    MODE_NIGHT_NO)
        }

        return binding.root
    }
}