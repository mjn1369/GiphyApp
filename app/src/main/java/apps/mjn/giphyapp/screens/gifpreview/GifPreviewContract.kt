package apps.mjn.giphyapp.screens.gifpreview

import apps.mjn.giphyapp.models.view.GifItemModel
import apps.mjn.giphyapp.mvp.BaseContract

/**
 * Created by mJafarinejad on 8/11/2018.
 */
interface GifPreviewContract {
    interface View : BaseContract.View {

        /**
         * Prepares and plays the specified gif in 'item' parameter
         */
        fun showGif(item: GifItemModel)
    }

    interface Presenter : BaseContract.Presenter<View> {

        /**
         * Requests a new random gif after 'delay' milliseconds
         */
        fun onNewRandomGifCountDown(delay: Long)
    }
}