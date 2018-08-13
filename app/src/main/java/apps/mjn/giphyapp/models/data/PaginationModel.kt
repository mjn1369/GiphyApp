package apps.mjn.giphyapp.models.data

import com.google.gson.annotations.SerializedName

/**
 * Created by mJafarinejad on 8/11/2018.
 */
data class PaginationModel(

        @SerializedName("total_count")
        var totalCount: Long,

        @SerializedName("count")
        var currentCount: Long,

        @SerializedName("offset")
        var offsetIndex: Long
)
