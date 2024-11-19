package com.app.recyclerviewmvvm.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

// BindingAdapter to load an image from a URL into an ImageView
@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String) {
    // Uses Glide to load the image from the provided URL and sets it into the ImageView
    Glide.with(view.context) // Uses context from the ImageView
        .load(url) // Loads the image from the URL
        .into(view) // Sets the loaded image into the ImageView
}
