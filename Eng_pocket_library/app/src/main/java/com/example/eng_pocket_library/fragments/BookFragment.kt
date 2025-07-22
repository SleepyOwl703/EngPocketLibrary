package com.example.eng_pocket_library.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eng_pocket_library.adapters.BookAdapter
import com.example.eng_pocket_library.models.BookItem
import com.example.eng_pocket_library.R
import com.example.eng_pocket_library.databinding.FragmentBookBinding
import kotlin.collections.ArrayList

class BookFragment : Fragment() {
    var list =ArrayList<BookItem>()
    lateinit var binding :FragmentBookBinding
    var myadapter : BookAdapter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        binding = FragmentBookBinding.inflate(inflater)
        list.add(BookItem("Godfather","Mario Puzo", R.string.bo_name))
        list.add(BookItem("Black Obelisk","Erich Maria Remarque", R.string.bo_name))
        list.add(BookItem("1984","George Orwell", R.string.bo_name))
        list.add(BookItem("All Quiet on the Western Front","Erich Maria Remarque", R.string.bo_name))
        list.add(BookItem("Shantaram","David Roberts", R.string.bo_name))
        list.add(BookItem("War and Peace","Lev Tolstoy", R.string.bo_name))
        list.add(BookItem("gone With the Wind","Margaret Mitchell", R.string.bo_name))
        list.add(BookItem("Cute bones","Alice Sebold", R.string.bo_name))
        list.add(BookItem("20000 lie under the water","Jul Vern", R.string.uts_name))
        list.add(BookItem("Knights of the space","Paul Andersen", R.string.bo_name))
        onSort()

        binding.rcView.hasFixedSize()
        binding.rcView.layoutManager= LinearLayoutManager(context)
        myadapter = context?.let { BookAdapter(list, it) }!!
        binding.rcView.adapter =myadapter
       // val nunito_font = ResourcesCompat.getFont(requireContext(),R.font.nunito_regular)
        return binding.root
    }
    companion object {
        @JvmStatic fun newInstance() {
        }
    }
    fun onSort(){
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
            return true
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                myadapter?.filter?.filter(p0)
                return true
            }

        })
    }
}