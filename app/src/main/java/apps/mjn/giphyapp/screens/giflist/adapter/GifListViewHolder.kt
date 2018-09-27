package apps.mjn.giphyapp.screens.giflist.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import apps.mjn.giphyapp.R
import apps.mjn.giphyapp.kx.getScreenWidthInPx
import apps.mjn.giphyapp.kx.gone
import apps.mjn.giphyapp.kx.loadUrlWithSuccessCallback
import apps.mjn.giphyapp.kx.visible
import apps.mjn.giphyapp.models.view.GifItemModel
import kotlinx.android.synthetic.main.item_giflist.view.*

/**
 * Created by mJafarinejad on 8/13/2018.
 */
class GifListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(item: GifItemModel, listener: GifListAdapter.GifClickedInterface) = with(itemView) {
        //Set image's height to 16:9 aspect ratio
        holderItemGifList.layoutParams.height = ((context.getScreenWidthInPx()/2) * (9.0/16.0)).toInt()

        pbItemGiftList.visible()
        tvItemGifListTitle.text = if(item.title.isNullOrBlank()) context.getString(R.string.no_title) else item.title
        ivItemGifList.loadUrlWithSuccessCallback(item.stillUrl, { pbItemGiftList.gone()})
        setOnClickListener { listener.onGifClicked(item) }
    }
}