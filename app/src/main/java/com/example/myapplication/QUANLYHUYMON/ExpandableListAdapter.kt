package com.example.myapplication.QUANLYHUYMON

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.example.myapplication.R
import kotlinx.android.synthetic.main.group_item.view.*
import kotlinx.android.synthetic.main.child_item.view.*

class ExpandableListAdapter(var context: Context,var body:MutableList<chitietdataItem>) :
    BaseExpandableListAdapter() {
    override fun getGroupCount(): Int {
        return body.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return body[groupPosition].SP.size
    }

    override fun getGroup(groupPosition: Int): String {
        return body[groupPosition].ID
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return body[groupPosition].SP
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
            convertView = inflater.inflate(R.layout.group_item,null)
        }
        convertView?.tv_nhanvien?.text= body[groupPosition].TENNV
        convertView?.tv_date?.text=body[groupPosition].TIME
        convertView?.tv_lydo?.text=body[groupPosition].LYDO

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
            convertView = inflater.inflate(R.layout.child_item,null)
        }
        convertView?.tv_masp?.text= body[groupPosition].SP[childPosition].TENSP
        convertView?.tv_soluong?.text= body[groupPosition].SP[childPosition].SOLUONG
        return convertView
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}