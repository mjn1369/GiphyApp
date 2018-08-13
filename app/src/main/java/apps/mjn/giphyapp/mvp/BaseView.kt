package apps.mjn.giphyapp.mvp

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import apps.mjn.giphyapp.GiphyApp
import apps.mjn.giphyapp.R

/**
 * Created by mJafarinejad on 8/11/2018.
 */
abstract class BaseView : AppCompatActivity() , BaseContract.View {

    override fun showMessage(message: String?) =
            Toast.makeText(this@BaseView, message, Toast.LENGTH_LONG).show()
}