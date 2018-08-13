package apps.mjn.giphyapp.screens.giflist.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
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
        holder_itemgiflist_gif.layoutParams.height = ((context.getScreenWidthInPx()/2) * (9.0/16.0)).toInt()
        pb_itemgiflist_loading.visible()
        tv_itemgiflist_title.text = if(item.title.isNullOrBlank()) "No Title" else item.title
        iv_itemgiflist_gif.loadUrlWithSuccessCallback(item.stillUrl, { pb_itemgiflist_loading.gone()})
        setOnClickListener { listener.onGifClicked(item) }
    }
}