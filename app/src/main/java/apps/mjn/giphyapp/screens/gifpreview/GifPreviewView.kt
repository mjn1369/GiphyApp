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

        val parcelableInput: Parcelable? = intent.getParcelableExtra(Constants.TAG_GIFVIEW)
        if(parcelableInput != null)
            currentGif = parcelableInput as GifItemModel
        else {
            finish()
            return
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
        animationRefreshProgress = ObjectAnimator.ofInt(pbGifPreviewDelay, "progress", 0, Constants.DELAY_RANDOM.toInt())
        animationRefreshProgress.duration = (Constants.DELAY_RANDOM * getAnimationScaleCoefficient()).toLong()
        animationRefreshProgress.interpolator = LinearInterpolator()
    }

    /**
     * Sets an OnPreparedListener for videoView
     */
    private fun setOnPlayListener() =
            vvGifPreview.setOnPreparedListener({ mp ->
                mp.isLooping = true
                hideLoading()
                startDelayProgress()
                vvGifPreview.start()
                presenter.onNewRandomGifCountDown(Constants.DELAY_RANDOM)
            })

    override fun showGif(item: GifItemModel) {
        showLoading()
        currentGif = item
        tvGifItemTitle.visible()
        if(currentGif.title.isNullOrBlank())
            tvGifItemTitle.gone()
        tvGifItemTitle.text = currentGif.title
        vvGifPreview.setVideoURI(Uri.parse(currentGif.videoUrl))
        vvGifPreview.requestFocus()
    }

    /**
     * Starts the delay progress bar
     */
    private fun startDelayProgress() {
        pbGifPreviewDelay.progress = 0
        animationRefreshProgress.start()
    }

    override fun showLoading() {
        pbGifPreviewLoading?.visible()
        cvGifPreview?.invisible()
    }

    override fun hideLoading() {
        pbGifPreviewLoading?.invisible()
        cvGifPreview?.visible()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }
}
