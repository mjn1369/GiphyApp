package apps.mjn.giphyapp.kx

import android.content.Context
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.os.Build
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import apps.mjn.giphyapp.models.data.GifObjectModel
import apps.mjn.giphyapp.models.data.PaginationModel
import apps.mjn.giphyapp.models.response.BaseResponse
import apps.mjn.giphyapp.models.view.GifItemModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by mJafarinejad on 8/12/2018.
 */

//region View Extensions
fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

/**
 * Loads the 'url' parameter into imageView and runs 'callback' on completion
 */
fun ImageView.loadUrlWithSuccessCallback(url: String?, callback: (Unit) -> Unit) {
    Glide.with(context).load(url).listener(object : RequestListener<Drawable> {

        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean =
                false

        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            callback(Unit)
            return false
        }

    }).into(this)
}

/**
 * Inflates the 'layoutId' parameter
 */
fun ViewGroup.inflate(layoutId: Int) = LayoutInflater.from(context).inflate(layoutId, this, false)
//endregion

//region Model Extensions

/**
 * Checks the API's response status from metaModel
 */
fun BaseResponse.isSuccessful() =
        metaModel.status < 300

/**
 * Creates and returns a GifItemModel based on GifObjectModel
 */
fun GifObjectModel.toGifItemModel() =
        GifItemModel(getStillUrl(), getVideoUrl(), title)

/**
 * Gets the still image url from GifObjectModel
 */
fun GifObjectModel.getStillUrl() =
        imagesListModel.fixedHeightStill.gifUrl

/**
 * Gets the video url from GifObjectModel
 */
fun GifObjectModel.getVideoUrl() =
        imagesListModel.originalMp4.mp4Url

/**
 * Checks if the PaginationModel has more items
 */
fun PaginationModel.hasMore() =
        currentCount + offsetIndex < totalCount
//endregion

//region Context Extensions

/**
 * Returns a coefficient to multiply animation duration, based on the animation duration setting in 'Developer Options'
 */
fun Context.getAnimationScaleCoefficient() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            1 / Settings.Global.getFloat(
                    getContentResolver(),
                    Settings.Global.ANIMATOR_DURATION_SCALE,
                    1.0F)
        }
        else
        {
            1 / Settings.System.getFloat(
                    getContentResolver(),
                    Settings.System.ANIMATOR_DURATION_SCALE,
                    1.0F)
        }

fun Context.getScreenWidthInPx(): Int {
    val display = (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
    val size = Point()
    display.getSize(size)
    return size.x
}
//endregion

//region Observable Extensions

/**
 * Sets default schedulers on observable
 */
fun <T:Any?> Observable<T>.setSchedulers() = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//endregion