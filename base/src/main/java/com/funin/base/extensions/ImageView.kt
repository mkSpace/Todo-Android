package com.funin.base.extensions

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.funin.base.image.GlideRequests

fun ImageView.loadImage(
    glideRequests: GlideRequests,
    imageUrl: String?,
    @DrawableRes placeholder: Int = 0,
    @DrawableRes errorImage: Int = 0,
) {
    glideRequests.load(imageUrl)
        .placeholder(placeholder)
        .error(errorImage)
        .into(this)
}

fun ImageView.loadImage(
    glideRequests: GlideRequests,
    @DrawableRes drawableResId: Int,
    @DrawableRes placeholder: Int = 0,
    @DrawableRes errorImage: Int = 0,
) {
    glideRequests.load(drawableResId)
        .placeholder(placeholder)
        .error(errorImage)
        .into(this)
}