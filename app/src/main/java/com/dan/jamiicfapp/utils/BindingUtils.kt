package com.dan.jamiicfapp.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("image_url")
fun loadImage(view: ImageView, url: String) {
    Glide.with(view).load(url).into(view)
}