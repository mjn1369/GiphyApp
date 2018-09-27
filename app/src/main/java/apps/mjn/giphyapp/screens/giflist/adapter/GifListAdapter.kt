package apps.mjn.giphyapp.screens.giflist.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import apps.mjn.giphyapp.R
import apps.mjn.giphyapp.kx.getScreenWidthInPx
import apps.mjn.giphyapp.kx.inflate
import apps.mjn.giphyapp.models.view.GifItemModel

class GifListAdapter(private var items: MutableList<GifItemModel>, private var gifClickCallback: GifClickedInterface) : RecyclerView.Adapter<GifListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifListViewHolder {
        var view = parent.inflate(R.layout.item_giflist)
        return GifListViewHolder(view)
    }

    override fun onBindViewHolder(holder: GifListViewHolder, position: Int) =
            holder.bind(items[position],gifClickCallback)

    fun add(item: GifItemModel) {
        items.add(item)
    }

    fun addAll(items: List<GifItemModel>) {
        for (item in items) {
            add(item)
        }
    }

    fun getItem(pos: Int): GifItemModel = items[pos]

    override fun getItemCount() = items.size

    interface GifClickedInterface {
        fun onGifClicked(item: GifItemModel)
    }
}
