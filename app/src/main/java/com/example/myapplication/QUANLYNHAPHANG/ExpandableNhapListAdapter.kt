package com.example.myapplication.QUANLYNHAPHANG

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.example.myapplication.R
import kotlinx.android.synthetic.main.groupnhap_item.view.*
import kotlinx.android.synthetic.main.childnhap_item.view.*

class ExpandableNhapListAdapter(var context: Context, var body:MutableList<chitietdataItemNhap>) :
    BaseExpandableListAdapter() {
    override fun getGroupCount(): Int {
        return body.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return body[groupPosition].SPNHAP.size
    }

    override fun getGroup(groupPosition: Int): String {
        return body[groupPosition].ID
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return body[groupPosition].SPNHAP
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        var convertView = convertView
        if (convertView==null){
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.groupnhap_item,null)
        }
        convertView?.tv_nhanvienNhap?.text= body[groupPosition].TENNV
        convertView?.tv_dateNhap?.text=body[groupPosition].TIME
        convertView?.tv_tongtienNhap?.text=(body[groupPosition].TONGTIEN.toInt()/1000).toString()+"K"
        return convertView
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        var convertView = convertView
        if (convertView==null){
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.childnhap_item,null)
        }
        convertView?.tv_masp?.text= body[groupPosition].SPNHAP[childPosition].TENSP
        convertView?.tv_soluong?.text= body[groupPosition].SPNHAP[childPosition].SOLUONG
        convertView?.tv_giasp?.text= (body[groupPosition].SPNHAP[childPosition].DONGIA.toInt()*body[groupPosition].SPNHAP[childPosition].SOLUONG.toInt()).toString()
        return convertView
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}