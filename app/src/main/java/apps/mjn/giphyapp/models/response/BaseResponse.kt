package apps.mjn.giphyapp.models.response

import apps.mjn.giphyapp.models.data.MetaModel
import com.google.gson.annotations.SerializedName

/**
 * Created by mJafarinejad on 8/11/2018.
 */
open class BaseResponse(

    @SerializedName("meta")
    val metaModel: MetaModel
) {
    fun isSuccessful() =
            metaModel.status < 300
}
