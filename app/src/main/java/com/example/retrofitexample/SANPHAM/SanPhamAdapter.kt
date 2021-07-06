package com.example.retrofitexample.SANPHAM
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitexample.R
import kotlinx.android.synthetic.main.ban_item.view.*
import kotlinx.android.synthetic.main.mon_item.view.*
class SanPhamAdapter(private val mModel: MutableList<SanPhamModel>, private val listener: SanPhamAdapter.OnItemClickListener):
    RecyclerView.Adapter<SanPhamAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.mon_item,parent,false)
        return PostViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentItem = mModel[position]
        holder.tvTENSP.text = currentItem.TENSP
        holder.tvGIASP.text = currentItem.GIASP
        holder.tvMASP.text = currentItem.MASP
    }

    override fun getItemCount(): Int {
        return mModel.size
    }

    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),View.OnClickListener{
        val tvTENSP: TextView = itemView.tv_mon
        val tvGIASP: TextView = itemView.tv_gia_mon
        val tvMASP: TextView = itemView.tv_soluong

        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(mModel[position])
            }
        }
    }
    interface OnItemClickListener {
        fun onItemClick(data: SanPhamModel)
    }
}

