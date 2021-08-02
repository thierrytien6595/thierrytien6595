package com.example.myapplication.TONKHO
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.mon_item.view.*
class SPAdapter(private val mModel: MutableList<SanPhamModel>, private val listener: SPAdapter.OnItemClickListener):
    RecyclerView.Adapter<SPAdapter.PostViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.mon_item,parent,false)
        return PostViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentItem = mModel[position]
        holder.tvTENSP.text = currentItem.TENSP
        holder.tvGIASP.text = currentItem.GIASP
        holder.tvSOLUONG.text = currentItem.SOLUONG.toString()
        val base_image_url = "http://192.168.1.5/thach/image/"
        Picasso.get().load(base_image_url+currentItem.HINHSP+".jpg").into(holder.imvHINHSP)
    }

    override fun getItemCount(): Int {
        return mModel.size
    }

    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),View.OnClickListener{
        val tvTENSP: TextView = itemView.tv_mon
        val tvGIASP: TextView = itemView.tv_gia_mon
        val imvHINHSP: ImageView = itemView.imv_mon
        val tvSOLUONG:TextView = itemView.tv_soluong
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
        fun onimvEDIT(MASP: String)
    }
}

