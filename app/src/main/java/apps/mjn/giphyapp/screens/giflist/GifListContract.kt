package apps.mjn.giphyapp.screens.giflist

import apps.mjn.giphyapp.mvp.BaseContract
import apps.mjn.giphyapp.models.view.GifItemModel

/**
 * Created by mJafarinejad on 8/11/2018.
 */
interface GifListContract {
    interface View : BaseContract.View {

        /**
         * Adds 'items' parameter to the end of the gif list
         */
        fun showItems(items: List<GifItemModel>)

        /**
         * Shows retry action at the bottom of the gif list
         */
        fun showRetryLoadMore()

        /**
         * Shows retry button in the middle of screen
         */
        fun showRetry()

        /**
         * Shows loadMore progress at the bottom of the gif list
         */
        fun showLoadMore()

        /**
         * Hides loadMore progress from the bottom of the gif list
         */
        fun hideLoadMore()
    }

    interface Presenter : BaseContract.Presenter<View> {

        /**
         * Gets more items from API
         */
        fun onMoreItems()
    }
}