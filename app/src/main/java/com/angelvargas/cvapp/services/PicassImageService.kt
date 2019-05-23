package com.angelvargas.cvapp.services

import android.widget.ImageView
import com.squareup.picasso.Picasso

class PicassImageService: ImageService {

    override fun loadImageInto(imageView: ImageView, imageUrl: String?) {
        if (!imageUrl.isNullOrEmpty()){
            Picasso.with(imageView.context).load(imageUrl)
                .into(imageView)
        }
    }
}