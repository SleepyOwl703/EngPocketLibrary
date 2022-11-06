package com.example.eng_pocket_library.fragments

import android.app.Dialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eng_pocket_library.R
import com.example.eng_pocket_library.adapters.WordAdapter
import kotlinx.android.synthetic.main.fragment_notebook.*
import com.example.eng_pocket_library.databinding.FragmentNotebookBinding
import com.example.eng_pocket_library.models.WordItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.alert_dialog_layout.*
import java.lang.reflect.Type


class NotebookFragment : Fragment(){
    lateinit var binding :FragmentNotebookBinding
    lateinit var myadapter :WordAdapter
    lateinit var  wordList : ArrayList<WordItem>

    //SHARED PREFERENCES
    val N_KEY : String = "NOTEBOOK_KEY"
    lateinit var sharedPreferences: SharedPreferences
    val WORD_KEY :String= "W_KEY"



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotebookBinding.inflate(inflater)
        //Try

        binding.onAddButton.setOnClickListener{
        var dialog : Dialog? = context?.let { Dialog(it) }
            dialog?.setContentView(R.layout.alert_dialog_layout)
            val width = (resources.displayMetrics.widthPixels*0.85).toInt()
            dialog!!.getWindow()?.setBackgroundDrawableResource(R.drawable.button_back_form)
            dialog!!.window?.setLayout(width,ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog?.show()


            dialog.onAddObjButton.setOnClickListener {
                var eng :String = dialog.inputEtEng.text.toString()
                var rus :String = dialog.inputEtRus.text.toString()
                dialog.cancel()
                wordList.add(WordItem(eng,rus))
               saveData()
                wordList = loadData()
                for(i:Int in 0 until wordList.size){
                Log.i("MyLog","-----${wordList[i].eng_word}")

            }
                if(wordList.size ==0){
                    Log.i("MyLog","NULL")
                }
            }
        }

        binding.wordRecView.layoutManager = LinearLayoutManager(context)
        binding.wordRecView.hasFixedSize()
        wordList = loadData()

        setRecyclerView(wordList,context)
        return binding.root
    }
    fun saveData(){
        sharedPreferences = context?.getSharedPreferences(N_KEY,MODE_PRIVATE)!!
        var gson:Gson = Gson()
        var json:String = gson.toJson(wordList)
        sharedPreferences.edit()
            .putString(WORD_KEY,json)
            .apply()
    }
    fun loadData() :ArrayList<WordItem>{
        sharedPreferences = context?.getSharedPreferences(N_KEY,MODE_PRIVATE)!!
        val emptylist = Gson().toJson(ArrayList<WordItem>())
        return Gson().fromJson(
            sharedPreferences.getString(WORD_KEY, emptylist),
            object : TypeToken<ArrayList<WordItem>>() {}.type
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun onAddNewObject(item:WordItem){
        wordList.add(item)
        saveData()
        //  myadapter.notifyItemInserted(wordList.size-1)
        //setRecyclerView(wordList,context)
    }
    fun setRecyclerView(list:ArrayList<WordItem>,con: Context?){
        myadapter = con?.let { WordAdapter(list, it) }!!
        myadapter.notifyDataSetChanged()
        binding.wordRecView.adapter = myadapter

        }
}