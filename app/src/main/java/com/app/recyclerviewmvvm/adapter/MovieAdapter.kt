package com.app.recyclerviewmvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.recyclerviewmvvm.databinding.ItemRecyclerviewMovieBinding
import com.app.recyclerviewmvvm.model.Movie

class MovieAdapter(private val clickListener: MovieListener) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val differCallback: DiffUtil.ItemCallback<Movie> = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.imdbID == newItem.imdbID
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    internal val differ: AsyncListDiffer<Movie> = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecyclerviewMovieBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = differ.currentList[position]
        holder.bind(movie, clickListener)
    }

    class MovieViewHolder(private val binding: ItemRecyclerviewMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, clickListener: MovieListener) {
            binding.movie = movie
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }
}

