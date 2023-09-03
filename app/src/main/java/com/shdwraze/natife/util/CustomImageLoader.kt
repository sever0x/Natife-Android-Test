package com.shdwraze.natife.util

import android.content.Context
import android.os.Build
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder

fun getImageLoader(context: Context): ImageLoader {
    return ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
}