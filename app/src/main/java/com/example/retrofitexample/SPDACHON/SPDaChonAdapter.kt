package com.example.retrofitexample.SPDACHON
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitexample.R
import com.example.retrofitexample.SANPHAM.SanPhamAdapter
import com.example.retrofitexample.SANPHAM.SanPhamModel
import kotlinx.android.synthetic.main.dachon_item.view.*
class SPDaChonAdapter(private val mModel: MutableList<SPDaChonModel>,private val listener: OnItemClickListener):
    RecyclerView.Adapter<SPDaChonAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.dachon_item,parent,false)
        return PostViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentItem = mModel[position]
        holder.tvTENSP.text = currentItem.TENSP
        holder.tvSOLUONG.text = currentItem.SOLUONG.toString()
        holder.tvCHUTHICH.text = currentItem.CHUTHICH
    }

    override fun getItemCount(): Int {
        return mModel.size
    }

    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),View.OnClickListener{
        val tvTENSP: TextView = itemView.tv_mon_dachon
        val tvSOLUONG: TextView = itemView.tv_soluong_dachon
        val tvCHUTHICH:TextView = itemView.tv_chuthich
        init {
            itemView.btn_chuthich.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemDaChonClick(mModel[position])
            }
        }
    }

    interface OnItemClickListener {
        fun onItemDaChonClick(data: SPDaChonModel)
    }
}

