package apps.mjn.giphyapp.di

import apps.mjn.giphyapp.screens.gifpreview.GifPreviewView
import apps.mjn.giphyapp.screens.giflist.GifListView
import dagger.Component
import javax.inject.Singleton

/**
 * Created by mJafarinejad on 8/11/2018.
 */

@Singleton
@Component(modules = arrayOf(NetworkModule::class, PresenterModule::class))
interface InjectorClass {
    fun inject(view: GifListView)
    fun inject(view: GifPreviewView)
}