package apps.mjn.giphyapp.models.response

import apps.mjn.giphyapp.models.data.GifObjectModel
import apps.mjn.giphyapp.models.data.MetaModel
import apps.mjn.giphyapp.models.data.PaginationModel
import com.google.gson.annotations.SerializedName

import java.util.ArrayList

/**
 * Created by mJafarinejad on 8/11/2018.
 */
class TrendingResponse(

        meta: MetaModel,

        @SerializedName("data")
        val gifObjectsList: ArrayList<GifObjectModel>,

        @SerializedName("pagination")
        var paginationModel: PaginationModel

) : BaseResponse(meta)

