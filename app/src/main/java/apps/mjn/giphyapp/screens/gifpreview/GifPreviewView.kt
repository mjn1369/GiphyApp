package apps.mjn.giphyapp.screens.gifpreview

import android.animation.ObjectAnimator
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.animation.LinearInterpolator
import apps.mjn.giphyapp.mvp.BaseView
import apps.mjn.giphyapp.Constants
import apps.mjn.giphyapp.GiphyApp
import apps.mjn.giphyapp.R
import apps.mjn.giphyapp.kx.getAnimationScaleCoefficient
import apps.mjn.giphyapp.kx.gone
import apps.mjn.giphyapp.kx.invisible
import apps.mjn.giphyapp.kx.visible
import apps.mjn.giphyapp.models.view.GifItemModel
import kotlinx.android.synthetic.main.activity_gifpreview.*
import javax.inject.Inject

class GifPreviewView : BaseView(), GifPreviewContract.View {

    @Inject
    lateinit var presenter: GifPreviewContract.Presenter

    private lateinit var currentGif: GifItemModel
    private lateinit var animationRefreshProgress: ObjectAnimator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gifpreview)
        injectDependencies()
        presenter.attach(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val parcelableInput: Parcelable = intent.getParcelableExtra(Constants.TAG_GIFVIEW)
        if(parcelableInput != null)
            currentGif = parcelableInput as GifItemModel
        else {
            showMessage("Null Input")
            finish()
        }
        initProgress()
        setOnPlayListener()
        showGif(currentGif)
    }

    private fun injectDependencies(){
        GiphyApp.getInjector()?.inject(this)
    }

    /**
     * Initializes delay progress view and its animation
     */
    private fun initProgress() {
        animationRefreshProgress = ObjectAnimator.ofInt(pb_gifpreview_delay, "progress", 0, Constants.DELAY_RANDOM.toInt())
        animationRefreshProgress.duration = (Constants.DELAY_RANDOM * getAnimationScaleCoefficient()).toLong()
        animationRefreshProgress.interpolator = LinearInterpolator()
    }

    /**
     * Sets an OnPreparedListener for videoView
     */
    private fun setOnPlayListener() =
            vv_gifpreview_gif.setOnPreparedListener({ mp ->
                mp.isLooping = true
                hideLoading()
                startDelayProgress()
                vv_gifpreview_gif.start()
                presenter.onNewRandomGifCountDown(Constants.DELAY_RANDOM)
            })

    override fun showGif(item: GifItemModel) {
        showLoading()
        currentGif = item
        tv_gifpreview_title.visible()
        if(currentGif.title.isNullOrBlank())
            tv_gifpreview_title.gone()
        tv_gifpreview_title.text = currentGif.title
        vv_gifpreview_gif.setVideoURI(Uri.parse(currentGif.videoUrl))
        vv_gifpreview_gif.requestFocus()
    }

    /**
     * Starts the delay progress bar
     */
    private fun startDelayProgress() {
        pb_gifpreview_delay.progress = 0
        animationRefreshProgress.start()
    }

    override fun showLoading() {
        pb_gifpreview_loading?.visible()
        cv_gifpreview?.invisible()
    }

    override fun hideLoading() {
        pb_gifpreview_loading?.invisible()
        cv_gifpreview?.visible()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.detach()
    }
}
