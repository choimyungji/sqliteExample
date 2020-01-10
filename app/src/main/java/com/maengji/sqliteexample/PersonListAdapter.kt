package com.maengji.sqliteexample

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.maengji.sqliteexample.vo.Person

class PersonListAdapter(val context: Context, val people: ArrayList<Person>): BaseAdapter() {

    override fun getCount(): Int {
        return people.size
    }

    override fun getItem(position: Int): Any {
        return people[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var holder: Holder

        if (convertView == null) {
            val newConvertView = LinearLayout(context)
            newConvertView.orientation = LinearLayout.HORIZONTAL

            val tvId = TextView(context)
            tvId.setPadding(10, 0, 20, 0)
            tvId.setTextColor(Color.rgb(0,0,0))

            val tvName = TextView(context)
            tvName.setPadding(20, 0, 20, 0)
            tvName.setTextColor(Color.rgb(0,0,0))

            val tvAge = TextView(context)
            tvAge.setPadding(20, 0, 20, 0)
            tvAge.setTextColor(Color.rgb(0,0,0))

            val tvPhone = TextView(context)
            tvPhone.setPadding(20, 0, 20, 0)
            tvPhone.setTextColor(Color.rgb(0,0,0))

            newConvertView.addView(tvId)
            newConvertView.addView(tvName)
            newConvertView.addView(tvAge)
            newConvertView.addView(tvPhone)

            holder = Holder(tvId, tvName, tvAge, tvPhone)

            newConvertView.tag = holder
        }
        else {
            holder = convertView.tag as Holder
        }

        val person = getItem(position) as Person
        holder.tvId.setText(person.id.toString())
        holder.tvName.setText(person.name)
        holder.tvAge.setText(person.age.toString())
        holder.tvPhone.setText(person.phone)

        return convertView!!
    }
}

class Holder(
    val tvId: TextView,
    val tvName: TextView,
    val tvAge: TextView,
    val tvPhone: TextView
)