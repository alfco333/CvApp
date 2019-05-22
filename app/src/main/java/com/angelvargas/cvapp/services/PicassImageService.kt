package com.angelvargas.cvapp.services

import android.widget.ImageView
import com.angelvargas.cvapp.R
import com.squareup.picasso.Picasso

class PicassImageService: ImageService {

    override fun loadImageInto(imageView: ImageView, imageUrl: String?) {
        if (!imageUrl.isNullOrEmpty()){
            Picasso.with(imageView.context).load(imageUrl)
                .resizeDimen(R.dimen.profile_image_size, R.dimen.profile_image_size )
                .into(imageView)
        }
    }
}