package com.example.thachcoffee.BAN
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.thachcoffee.R
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
        if(currentItem.TONGTIEN!=0) {
            holder.tvtongtien.text = currentItem.TONGTIEN.toString()
        }
        if (currentItem.TRANGTHAI==1) holder.bancolor.setBackgroundResource(R.color.green)
        else holder.bancolor.setBackgroundResource(R.color.purple_700)
    }
    override fun getItemCount() = banList.size
        inner class BanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
            val tenBan: TextView = itemView.tv_tenban
            val bancolor:RelativeLayout = itemView.bancolor
            val tvtongtien:TextView = itemView.tv_tongtien
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