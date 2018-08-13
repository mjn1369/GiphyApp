package apps.mjn.giphyapp.models.data

import com.google.gson.annotations.SerializedName

/**
 * Created by mJafarinejad on 8/11/2018.
 */
data class MetaModel(

    @SerializedName("status")
    val status: Int = 0,

    @SerializedName("msg")
    val message: String? = null,

    @SerializedName("response_id")
    val responseId: String? = null
)
