package com.example.eng_pocket_library.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.eng_pocket_library.BookActivity
import com.example.eng_pocket_library.R
import com.example.eng_pocket_library.models.BookItem
import java.util.ArrayList
import kotlin.random.Random

class BookAdapter(alist:ArrayList<BookItem>, acontext: Context): RecyclerView.Adapter<BookAdapter.ViewHolder>() ,Filterable {
    private var list = alist
    private var slist = ArrayList<BookItem>()
    private var context = acontext

    init{
        slist.addAll(list)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        return ViewHolder(inflater.inflate(R.layout.book_item_layout,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var book = list.get(position)
        holder.bind(book,context)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view : View):RecyclerView.ViewHolder(view){
        var colors= listOf<Int>(
            R.color.deep_blue, R.color.light_blue_600, R.color.light_blue,
            R.color.purple_500
        )
        var i = colors[Random.nextInt(0,3)]
        private var book_title = view.findViewById<TextView>(R.id.book_title)
        private var book_author = view.findViewById<TextView>(R.id.book_author)
        private var book_back = view.findViewById<ConstraintLayout>(R.id.book_background_lay)
        fun bind(book: BookItem, context:Context){
            book_title.text = book.title
            book_author.text = book.author
            book_back.setBackgroundColor(context.resources.getColor(i))


            itemView.setOnClickListener(View.OnClickListener {
                var intent =Intent(context, BookActivity::class.java)
                intent.putExtra("bookTitle",book.title)
                intent.putExtra("bookText",book.text)
                context.startActivity(intent)
                            })
        }
    }

    override fun getFilter(): Filter {
      return object : Filter() {
          override fun performFiltering(charSequence: CharSequence?): FilterResults {
              var filteredList = ArrayList<BookItem>()
              if (charSequence == null || charSequence.isEmpty()) {
                  filteredList.addAll(slist)
              } else {
                  for (i in slist) {
                      if (i.title.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                          filteredList.add(i);
                      }
                  }
              }

              var filterResults= FilterResults();
              filterResults.values = filteredList;
              return filterResults
          }

          override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
              list.clear()
              list.addAll(p1?.values as Collection<BookItem>)
              notifyDataSetChanged()

          }
      }
    }

}