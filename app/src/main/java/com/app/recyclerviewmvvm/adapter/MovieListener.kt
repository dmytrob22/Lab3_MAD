package com.app.recyclerviewmvvm.adapter

import com.app.recyclerviewmvvm.model.Movie

class MovieListener(private val clickListener: (Movie) -> Unit) {
    fun onClick(movie: Movie) {
        clickListener(movie)
    }
}
