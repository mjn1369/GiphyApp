package apps.mjn.giphyapp.screens.gifpreview

import apps.mjn.giphyapp.Constants
import apps.mjn.giphyapp.kx.setSchedulers
import apps.mjn.giphyapp.mvp.BasePresenter
import apps.mjn.giphyapp.network.ApiInterface
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by mJafarinejad on 8/11/2018.
 */
class GifPreviewPresenter @Inject constructor(var api: ApiInterface) : BasePresenter<GifPreviewContract.View>(), GifPreviewContract.Presenter {

    override fun onNewRandomGifCountDown(delay: Long) {
        addDisposable(
                Observable
                        .timer(delay, TimeUnit.MILLISECONDS)
                        .setSchedulers()
                        .subscribe {
                            getRandom()
                        }
        )
    }

    /**
     * Gets a new random gif
     */
    private fun getRandom() =
            api.getRandom(Constants.API_KEY)
                    .setSchedulers()
                    .subscribe(
                            {
                                if (it.isSuccessful()) {
                                    getView()?.showGif(it.gifObjectModel.toGifItemModel())
                                } else {
                                    onNewRandomGifCountDown(Constants.DELAY_RANDOM)
                                }
                            },
                            {
                                onNewRandomGifCountDown(Constants.DELAY_RANDOM)
                            }
                    )
}