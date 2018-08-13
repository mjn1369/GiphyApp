package apps.mjn.giphyapp.models.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by mJafarinejad on 8/11/2018.
 */
data class ImageModel(

    @SerializedName("url")
    val gifUrl: String,

    @SerializedName("mp4")
    val mp4Url: String

) : Serializable
