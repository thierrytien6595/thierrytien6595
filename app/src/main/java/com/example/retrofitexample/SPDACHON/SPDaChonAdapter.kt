package com.example.retrofitexample.SPDACHON
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitexample.R
import kotlinx.android.synthetic.main.dachon_item.view.*
class SPDaChonAdapter(private val mModel: MutableList<SPDaChonModel>):
    RecyclerView.Adapter<SPDaChonAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.dachon_item,parent,false)
        return PostViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentItem = mModel[position]
        holder.tvTENSP.text = currentItem.TENSP
        holder.tvSOLUONG.text = currentItem.SOLUONG.toString()
    }

    override fun getItemCount(): Int {
        return mModel.size
    }

    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvTENSP: TextView = itemView.tv_mon_dachon
        val tvSOLUONG: TextView = itemView.tv_soluong_dachon

    }
}

