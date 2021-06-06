package com.example.myrxjava


import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CustomAdapter(
    context: Context,
    resource: Int,
    listData: MutableList<CurrancyItems>,
    private val layoutInflater: LayoutInflater
) :
    ArrayAdapter<CurrancyItems?>(context, resource, listData as List<CurrancyItems?>) {
    private val listData: MutableList<CurrancyItems>

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val currancyItem: CurrancyItems? = getItem(position)
        val holder: ViewHolder
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_layout, null, true)
            holder = ViewHolder()
            holder.codeName = convertView.findViewById(R.id.codeName)
            holder.name = convertView.findViewById(R.id.name)
            holder.value = convertView.findViewById(R.id.Value)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        if (currancyItem != null) {
            holder.codeName?.setText(currancyItem.CharCode)
        }
        if (currancyItem != null) {
            holder.value!!.text = java.lang.Double.toString(currancyItem.Value)
        }
        if (currancyItem != null) {
            holder.name?.setText(currancyItem.Name)
        }
        if (currancyItem != null) {
            if (currancyItem.Value > currancyItem.Previous) {
                holder.value!!.setTextColor(Color.GREEN)
            } else if (currancyItem.Value < currancyItem.Previous) {
                holder.value!!.setTextColor(Color.RED)
            } else {
                holder.value!!.setTextColor(Color.WHITE)
            }
        }
        return convertView!!
    }

    private class ViewHolder {
        var codeName: TextView? = null
        var name: TextView? = null
        var value: TextView? = null
    }

    fun update(items: List<CurrancyItems>?) {
        listData.clear()
        listData.addAll(items!!)
        notifyDataSetChanged()
    }

    init {
        this.listData = listData
    }
}