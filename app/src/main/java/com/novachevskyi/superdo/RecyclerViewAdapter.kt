package com.novachevskyi.superdo

import android.content.Context
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.graphics.drawable.GradientDrawable
import android.widget.TextView

class RecyclerViewAdapter internal constructor(private val context: Context, data: List<DataModel>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private val data: MutableList<DataModel> = data.toMutableList()
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var clickListener: ItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.shop_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shopItem = data[position]

        val bgShape = holder.circleView.background as GradientDrawable
        bgShape.setColor(Color.parseColor(shopItem.bagColor))

        holder.product.text = shopItem.name
        holder.weight.text = shopItem.weight
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        var circleView: ImageView = itemView.findViewById(R.id.circle)
        var product: TextView = itemView.findViewById(R.id.product)
        var weight: TextView = itemView.findViewById(R.id.weight)

        override fun onClick(view: View) {
            if (clickListener != null) clickListener?.onItemClick(view, adapterPosition)
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    fun getItem(id: Int): DataModel {
        return data[id]
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

    fun addItem(item: DataModel) {
        data.add(0, item)
        notifyItemInserted(0)
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }
}
