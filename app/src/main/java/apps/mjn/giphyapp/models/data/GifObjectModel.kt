package apps.mjn.giphyapp.models.data

import com.google.gson.annotations.SerializedName

/**
 * Created by mJafarinejad on 8/11/2018.
 */
data class GifObjectModel(

        @SerializedName("title")
        val title: String,

        @SerializedName("images")
        val imagesListModel: ImagesListModel
)
