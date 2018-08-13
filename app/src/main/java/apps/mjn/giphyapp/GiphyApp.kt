package apps.mjn.giphyapp

import android.app.Application
import apps.mjn.giphyapp.di.DaggerInjectorClass
import apps.mjn.giphyapp.di.InjectorClass
import apps.mjn.giphyapp.di.NetworkModule
import apps.mjn.giphyapp.di.PresenterModule

/**
 * Created by mJafarinejad on 8/11/2018.
 */
class GiphyApp : Application() {

    companion object {
        private var injectorClass: InjectorClass? = null
        fun getInjector() = injectorClass
    }

    override fun onCreate() {
        super.onCreate()
        injectorClass = initDagger()
    }

    /**
     * Initializes dagger injector component
     */
    private fun initDagger(): InjectorClass = DaggerInjectorClass.builder()
            .networkModule(NetworkModule())
            .presenterModule(PresenterModule())
            .build()
}