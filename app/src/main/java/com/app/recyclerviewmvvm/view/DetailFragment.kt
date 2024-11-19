package com.app.recyclerviewmvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.app.recyclerviewmvvm.R
import com.app.recyclerviewmvvm.databinding.FragmentDetailBinding

// Fragment to display details of a selected movie
class DetailFragment : Fragment() {

    // Inflates the layout for the fragment and binds data to the UI
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Data binding initialization
        val binding: FragmentDetailBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detail, // Fragment's layout resource
            container,
            false // Indicates that the fragment's root view is not attached yet
        )

        // Retrieves arguments passed to the fragment (movie data)
        val args: DetailFragmentArgs by navArgs()
        val movie = args.movie

        // Binds the movie data to the UI (binding the movie object to the layout)
        binding.movie = movie

        return binding.root
    }
}
