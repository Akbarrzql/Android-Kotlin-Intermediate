package com.example.learnkotlinintermediate.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.learnkotlinintermediate.R
import com.example.learnkotlinintermediate.model.Post


class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>(){

    private var myList = emptyList<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.userId_txt.text = myList[position].userId.toString()
        holder.id_txt.text = myList[position].id.toString()
        holder.title_txt.text = myList[position].title
        holder.body_txt.text = myList[position].body
    }

    override fun getItemCount(): Int {
        return myList.size
    }


    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var userId_txt = itemView.findViewById<TextView>(R.id.userId_txt)
        var id_txt = itemView.findViewById<TextView>(R.id.id_txt)
        var title_txt = itemView.findViewById<TextView>(R.id.title_txt)
        var body_txt = itemView.findViewById<TextView>(R.id.body_txt)
    }

    fun setData(newList: List<Post>) {
        myList = newList
        notifyDataSetChanged()
    }
}