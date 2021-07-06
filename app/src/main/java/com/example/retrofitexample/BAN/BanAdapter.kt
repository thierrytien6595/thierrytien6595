package com.example.retrofitexample.BAN
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitexample.R
import kotlinx.android.synthetic.main.ban_item.view.*
class BanAdapter(private val banList: MutableList<BanModel>, private val listener: BanAdapter.OnItemClickListener) :
    RecyclerView.Adapter<BanAdapter.BanViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BanViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.ban_item,
            parent, false)
        return BanViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: BanViewHolder, position: Int) {
        val currentItem = banList[position]
        holder.tenBan.text = currentItem.TENBAN
    }
    override fun getItemCount() = banList.size
        inner class BanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
            val tenBan: TextView = itemView.tv_tenban
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(banList[position])
            }
        }
    }
    interface OnItemClickListener {
        fun onItemClick(data: BanModel)
    }
}