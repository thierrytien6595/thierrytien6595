package com.example.retrofitexample.THEMXOASP
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitexample.R
import com.example.retrofitexample.SPDACHON.SPDaChonModel
import kotlinx.android.synthetic.main.spdc_item.view.*

class TachDonAdapter(private val mModel: MutableList<SPDaChonModel>, private val listener: OnItemClickListener):
    RecyclerView.Adapter<TachDonAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TachDonAdapter.PostViewHolder {
        var itemview =
            LayoutInflater.from(parent.context).inflate(R.layout.spdc_item, parent, false)
        return PostViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: TachDonAdapter.PostViewHolder, position: Int) {
        val currentItem = mModel[position]
        holder.tvTENSP.text = currentItem.TENSP
        holder.tvSOLUONG.text = currentItem.SOLUONG.toString()
        holder.tvCHUTHICH.text = currentItem.CHUTHICH
            holder.btn_GIAM.visibility = View.VISIBLE
            holder.btn_GIAM.setOnClickListener(){

                listener.onBtnGiamClick(currentItem)
            }

    }
        override fun getItemCount(): Int {
            return mModel.size
        }

        inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener {
            val tvTENSP: TextView = itemView.tv_mon_dachon_spdc
            val tvSOLUONG: TextView = itemView.tv_soluong_dachon_spdc
            val tvCHUTHICH: TextView = itemView.tv_chuthich_spdc
            val btn_GIAM:Button = itemView.btn_giam
            init {
//                itemView.btn_chuthich?.setOnClickListener(this)
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
            fun onBtnGiamClick(data: SPDaChonModel)
        }
    }

