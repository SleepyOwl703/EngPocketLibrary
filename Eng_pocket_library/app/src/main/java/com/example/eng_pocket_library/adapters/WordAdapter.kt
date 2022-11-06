package com.example.eng_pocket_library.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eng_pocket_library.R
import com.example.eng_pocket_library.models.WordItem

class WordAdapter(alist:ArrayList<WordItem>,context:Context) : RecyclerView.Adapter<WordAdapter.ViewHolder>() {
    private var list = alist
    private var con =context

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var etv:TextView = itemView.findViewById(R.id.eng_tv)
        private var rtv:TextView = itemView.findViewById(R.id.rus_tv)
        fun bind(word:WordItem){
            etv.text = word.eng_word
            rtv.text = word.rus_word
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
         val inflater = LayoutInflater.from(con)
        return ViewHolder(inflater.inflate(R.layout.special_word_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         var word:WordItem = list.get(position)
        holder.bind(word)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}