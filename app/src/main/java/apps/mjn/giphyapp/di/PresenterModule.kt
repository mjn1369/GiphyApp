package apps.mjn.giphyapp.di

import apps.mjn.giphyapp.network.ApiInterface
import apps.mjn.giphyapp.screens.gifpreview.GifPreviewContract
import apps.mjn.giphyapp.screens.gifpreview.GifPreviewPresenter
import apps.mjn.giphyapp.screens.giflist.GifListContract
import apps.mjn.giphyapp.screens.giflist.GifListPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by mJafarinejad on 8/11/2018.
 */

@Module
class PresenterModule {

    @Provides
    fun provideGifsListPresenter(api: ApiInterface): GifListContract.Presenter =
            GifListPresenter(api)

    @Provides
    fun provideGifDetailsPresenter(api: ApiInterface): GifPreviewContract.Presenter =
            GifPreviewPresenter(api)
}