package apps.mjn.giphyapp.models.data

import apps.mjn.giphyapp.models.view.GifItemModel
import com.google.gson.annotations.SerializedName

/**
 * Created by mJafarinejad on 8/11/2018.
 */
data class GifObjectModel(

        @SerializedName("title")
        val title: String,

        @SerializedName("images")
        val imagesListModel: ImagesListModel
) {

        /**
        * Creates and returns a GifItemModel based on GifObjectModel
        */
        fun toGifItemModel() =
        GifItemModel(getStillUrl(), getVideoUrl(), title)

        /**
         * Gets the still image url from GifObjectModel
         */
        fun getStillUrl() =
                imagesListModel.fixedHeightStill.gifUrl

        /**
         * Gets the video url from GifObjectModel
         */
        fun getVideoUrl() =
                imagesListModel.originalMp4.mp4Url
}
