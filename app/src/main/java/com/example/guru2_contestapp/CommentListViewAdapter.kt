package com.example.guru2_contestapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class  CommentListViewAdapter(val context: Context, val commentList: ArrayList<CommentListViewItem>): BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.comment_list, null)

        //val img = view.findViewById<ImageView>(R.id.WimageView)
        val name = view.findViewById<TextView>(R.id.WnameTextView)
        val comment = view.findViewById<TextView>(R.id.WcommentTextView)
        val date = view.findViewById<TextView>(R.id.WdateTextView)

        val commentlist = commentList[position]

        //img.setImageResource(R.drawable.airportbaby)
        name.text = commentlist.name
        comment.text = commentlist.comment
        date.text = commentlist.date

        return view
    }

    override fun getItem(position: Int): Any {
        return commentList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return commentList.size
    }
}