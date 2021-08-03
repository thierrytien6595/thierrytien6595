package com.example.myapplication.TONKHO
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import kotlinx.android.synthetic.main.them_item.view.*

class ThemAdapter(private val mModel: MutableList<SanPhamModel>, private val listener: ThemAdapter.OnItemClickListener):
    RecyclerView.Adapter<ThemAdapter.PostViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.them_item,parent,false)
        return PostViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentItem = mModel[position]
        holder.tvMon.text = currentItem.TENSP
        holder.tvSoluong.text = currentItem.SOLUONG.toString()
        holder.tvSoluong.setOnClickListener(){
            listener.onClickTextView(currentItem)
        }

        holder.btnADD.setOnClickListener(){
            listener.onClickAddBtn(currentItem)
        }
        holder.btnREMOVE.setOnClickListener(){
            listener.onClickRemoveBtn(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return mModel.size
    }

    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvMon: TextView = itemView.tv_mon
        val tvSoluong:TextView = itemView.tv_soluong
        val btnADD: Button = itemView.btn_add
        val btnREMOVE: Button = itemView.btn_remove
    }
    interface OnItemClickListener {
        fun onClickTextView(data: SanPhamModel)
        fun onClickAddBtn(data: SanPhamModel)
        fun onClickRemoveBtn(data: SanPhamModel)
    }
}

