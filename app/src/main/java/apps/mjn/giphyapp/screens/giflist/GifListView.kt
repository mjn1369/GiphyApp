package apps.mjn.giphyapp.screens.giflist

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import apps.mjn.giphyapp.mvp.BaseView
import apps.mjn.giphyapp.Constants
import apps.mjn.giphyapp.GiphyApp
import apps.mjn.giphyapp.R
import apps.mjn.giphyapp.screens.gifpreview.GifPreviewView
import apps.mjn.giphyapp.kx.gone
import apps.mjn.giphyapp.kx.visible
import apps.mjn.giphyapp.models.view.GifItemModel
import apps.mjn.giphyapp.screens.giflist.adapter.GifListAdapter
import kotlinx.android.synthetic.main.activity_giflist.*
import javax.inject.Inject

class GifListView : BaseView(), GifListContract.View, GifListAdapter.GifClickedInterface {

    lateinit var adapter: GifListAdapter
    lateinit var layoutManager: RecyclerView.LayoutManager
    private var isLoading = false

    @Inject
    lateinit var presenter: GifListContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_giflist)
        injectDependencies()
        initList()
        setupListeners()
        presenter.attach(this)
    }

    private fun injectDependencies(){
        GiphyApp.getInjector()?.inject(this)
    }

    /**
     * Initialize RecyclerView's layoutManager & adapter as Empty
     */
    private fun initList() {
        layoutManager = GridLayoutManager(this, 2)
        adapter = GifListAdapter(arrayListOf(), this)
        rv_giflist_items.layoutManager = layoutManager
        rv_giflist_items.adapter = adapter
    }

    /**
     * Setup click and scroll listeners for ui elements
     */
    private fun setupListeners() {

        //Setup click listeners
        btn_giflist_retry.setOnClickListener { presenter.onMoreItems() }
        iv_giflist_loadmore.setOnClickListener { presenter.onMoreItems() }

        //Setup scroll listener
        rv_giflist_items.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {

                //check if the last visible item is actually the last item
                if ((recyclerView?.layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1) {
                    if (!isLoading) {
                        isLoading = true
                        presenter.onMoreItems()

                        //scroll to exact bottom
                        rv_giflist_items.post({
                            rv_giflist_items.smoothScrollToPosition(adapter.itemCount + 1)
                        })
                    }
                }
            }
        })
    }

    override fun showItems(result: List<GifItemModel>) {
        isLoading = false
        adapter.addAll(result.filter { it.stillUrl!=null })
        adapter.notifyItemRangeInserted(adapter.itemCount-result.size, adapter.itemCount)
    }

    override fun showLoading() {
        btn_giflist_retry.gone()
        pb_giflist_loading.visible()
    }

    override fun hideLoading() {
        btn_giflist_retry?.gone()
        pb_giflist_loading?.gone()
    }

    override fun showRetry() {
        pb_giflist_loading?.gone()
        btn_giflist_retry?.visible()
    }

    override fun showRetryLoadMore() {
        holder_giflist_loadmore?.visible()
        pb_giflist_loadmore?.gone()
        iv_giflist_loadmore?.visible()
    }

    override fun showLoadMore() {
        holder_giflist_loadmore?.visible()
        pb_giflist_loadmore?.visible()
        iv_giflist_loadmore?.gone()
    }

    override fun hideLoadMore() {
        holder_giflist_loadmore?.gone()
    }

    /**
     * Starts GifItem activity to show the selected Gif
     */
    override fun onGifClicked(item: GifItemModel) {
        if(item.videoUrl != null) {
            val intent = Intent(this@GifListView, GifPreviewView::class.java)
            intent.putExtra(Constants.TAG_GIFVIEW, item)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }
}
