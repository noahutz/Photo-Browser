package com.noahutz.photobrowser.util

import android.widget.ImageView
import com.squareup.picasso.Picasso

abstract class ImageLoader {
    abstract fun loadImage(imageUrl: String, imageView: ImageView)
}

class PicassoImageLoader : ImageLoader() {
    private val instance = Picasso.get()

    override fun loadImage(imageUrl: String, imageView: ImageView) {
        instance.load(imageUrl).into(imageView)
    }
}