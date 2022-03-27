package app.oways.luckytrip.util.extentions

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

fun AppCompatImageView.load(
    url: String?
) {
    Glide.with(this.context)
        .load(url)
        .apply(RequestOptions.bitmapTransform(RoundedCorners(14)))
        .format(DecodeFormat.PREFER_RGB_565)
        .into(this)
}
