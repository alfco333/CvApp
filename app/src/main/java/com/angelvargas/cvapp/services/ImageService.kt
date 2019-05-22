package com.angelvargas.cvapp.services

import android.widget.ImageView

interface ImageService {

    fun loadImageInto(imageView: ImageView, imageUrl: String?)
}