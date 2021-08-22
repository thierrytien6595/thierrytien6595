package com.example.myapplication.QUANLYNHANVIEN
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import kotlinx.android.synthetic.main.lich_item.view.*

class LichSuNVAdapter(private val mModel: MutableList<lichsunhanvienitem>):
    RecyclerView.Adapter<LichSuNVAdapter.PostViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.lich_item,parent,false)
        return PostViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentItem = mModel[position]
        holder.tvNGAY.text = currentItem.NGAY
        holder.tvTIMEIN.text = currentItem.TIMEIN
        holder.tvTIMEOUT.text = currentItem.TIMEOUT
        holder.tvWORKTIME.text = currentItem.WORKTIME
        holder.tvDIEMTRU.text = currentItem.DIEMTRU
        holder.tvTENNV.text = currentItem.TENNV
    }

    override fun getItemCount(): Int {
        return mModel.size
    }

    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvNGAY: TextView = itemView.tv_ngay
        val tvTIMEIN:TextView = itemView.tv_timein
        val tvTIMEOUT:TextView = itemView.tv_timeout
        val tvWORKTIME:TextView = itemView.tv_worktime
        val tvDIEMTRU:TextView = itemView.tv_diemtru
        val tvTENNV:TextView = itemView.tv_tennv
    }
}

