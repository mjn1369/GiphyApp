package apps.mjn.giphyapp.mvp

/**
 * Created by mJafarinejad on 8/11/2018.
 */
interface BaseContract {
    interface View{

        /**
         * Shows loading on screen
         */
        fun showLoading()

        /**
         * Hides loading from screen
         */
        fun hideLoading()

        /**
         * Show a message on screen
         */
        fun showMessage(message:String?)
    }
    interface Presenter<V:View>{

        /**
         * Attaches a View to Presenter
         */
        fun attach(view: V)

        /**
         * Detaches View from Presenter
         */
        fun detach()
    }
}