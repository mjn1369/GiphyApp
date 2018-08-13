package apps.mjn.giphyapp.screens.giflist

import android.util.Log
import apps.mjn.giphyapp.mvp.BasePresenter
import apps.mjn.giphyapp.Constants
import apps.mjn.giphyapp.kx.hasMore
import apps.mjn.giphyapp.kx.isSuccessful
import apps.mjn.giphyapp.kx.setSchedulers
import apps.mjn.giphyapp.kx.toGifItemModel
import apps.mjn.giphyapp.models.data.PaginationModel
import apps.mjn.giphyapp.models.response.TrendingResponse
import apps.mjn.giphyapp.network.ApiInterface
import javax.inject.Inject

/**
 * Created by mJafarinejad on 8/11/2018.
 */
class GifListPresenter @Inject constructor(val api:ApiInterface) : BasePresenter<GifListContract.View>(), GifListContract.Presenter {
    private var page = 0L
    private val limit = 20L
    private var hasMore = true

    override fun attach(v: GifListContract.View) {
        super.attach(v)
        onMoreItems()
    }

    override fun onMoreItems() {
        if (hasMore) {
            showLoadingItems()
            addDisposable(
                    api.getTrending(Constants.API_KEY, limit, getOffset())
                            .setSchedulers()
                            .subscribe(
                                    {
                                        if (it.isSuccessful()) {
                                            sendSuccess(it)
                                            updatePagination(it.paginationModel)
                                        } else {
                                            sendFailure(it.metaModel.message)
                                        }
                                    },
                                    {
                                        sendFailure(it.localizedMessage)
                                    }
                            )
            )
        }
    }

    /**
     * Gets correct offset for current page
     */
    private fun getOffset() =
            page * limit

    /**
     * Decides the loading behavior and shows it on view
     */
    private fun showLoadingItems() {
        if(isFirstPage())
            getView()?.showLoading()
        else
            getView()?.showLoadMore()
    }

    /**
     * Sends retrieved items from API to view
     */
    private fun sendSuccess(response: TrendingResponse) {
        if (isFirstPage())
            getView()?.hideLoading()
        else
            getView()?.hideLoadMore()
        getView()?.showItems(response.gifObjectsList.map { it.toGifItemModel() })
    }

    /**
     * Send failed message to view
     */
    private fun sendFailure(message: String?) {
        if (isFirstPage())
            getView()?.showRetry()
        else
            getView()?.showRetryLoadMore()
        getView()?.showMessage(message)
    }

    /**
     * Update
     */
    private fun updatePagination(paginationModel: PaginationModel) {
        page = 1 + (paginationModel.offsetIndex / limit)
        hasMore = paginationModel.hasMore()
    }

    private fun isFirstPage() = page == 0L
}