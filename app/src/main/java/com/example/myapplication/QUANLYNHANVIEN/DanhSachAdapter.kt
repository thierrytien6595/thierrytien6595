package com.example.myapplication.QUANLYNHANVIEN
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import kotlinx.android.synthetic.main.danhsachnv.view.*

class DanhSachAdapter(private val mModel: MutableList<danhsachmodel>, private val listener: DanhSachAdapter.OnItemClickListener):
    RecyclerView.Adapter<DanhSachAdapter.PostViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.danhsachnv,parent,false)
        return PostViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentItem = mModel[position]
        holder.tvTENNV.text = currentItem.TENNV
        holder.tvSDT.text = currentItem.SDT
        holder.btnSUANV.setOnClickListener{
            listener.onSUANVClick(currentItem)
        }
        holder.btnXOANV.setOnClickListener{
            listener.onXOANVClick(currentItem)
        }

    }

    override fun getItemCount(): Int {
        return mModel.size
    }

    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvTENNV: TextView = itemView.danhsach_tennv
        val tvSDT:TextView = itemView.danhsach_sdt
        val btnSUANV:Button = itemView.btn_suanv
        val btnXOANV:Button = itemView.btn_xoanv
    }
    interface OnItemClickListener {
        fun onSUANVClick(currentItem: danhsachmodel)
        fun onXOANVClick(currentItem: danhsachmodel)
    }
}

