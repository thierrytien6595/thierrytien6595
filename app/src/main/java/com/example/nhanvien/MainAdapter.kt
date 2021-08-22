package com.example.nhanvien
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nhanvien.API.timeItem
import kotlinx.android.synthetic.main.lichsu_item.view.*

class MainAdapter(private val mModel: MutableList<timeItem>, private val listener: MainActivity):
    RecyclerView.Adapter<MainAdapter.PostViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.lichsu_item,parent,false)
        return PostViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentItem = mModel[position]
        holder.tvTIMEIN.text = currentItem.TIMEIN
        holder.tvTIMEOUT.text = currentItem.TIMEOUT
        holder.tv_THU.text = currentItem.THU
        holder.tv_NGAY.text = currentItem.NGAY
        holder.tv_WORKTIME.text = currentItem.WORKTIME
        holder.tv_DIEMTRU.text = currentItem.DIEMTRU
    }
    override fun getItemCount(): Int {
        return mModel.size
    }

    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvTIMEIN: TextView = itemView.tv_timein
        val tvTIMEOUT: TextView = itemView.tv_timeout
        val tv_THU:TextView = itemView.tv_thu
        val tv_NGAY:TextView = itemView.tv_ngay
        val tv_WORKTIME:TextView = itemView.tv_giolam
        val tv_DIEMTRU:TextView = itemView.tv_diemtru
    }
    interface OnItemClickListener {
        fun onItemClick(data: timeItem)
        fun onimvEDIT(MASP: String)
    }
}

