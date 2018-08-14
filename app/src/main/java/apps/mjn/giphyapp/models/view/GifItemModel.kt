package apps.mjn.giphyapp.models.view

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by mJafarinejad on 8/13/2018.
 */
@Parcelize
data class GifItemModel(val stillUrl: String?, val videoUrl: String?, val title: String?) : Parcelable