package apps.mjn.giphyapp.models.data

import com.google.gson.annotations.SerializedName

/**
 * Created by mJafarinejad on 8/13/2018.
 */
data class ImagesListModel (

        @SerializedName("fixed_height_still")
        val fixedHeightStill: ImageModel,

        @SerializedName("original_mp4")
        val originalMp4: ImageModel
)