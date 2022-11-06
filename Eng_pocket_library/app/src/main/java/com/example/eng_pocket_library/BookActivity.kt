package com.example.eng_pocket_library

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ScrollView
import com.example.eng_pocket_library.databinding.ActivityBookActivityBinding
import kotlinx.android.synthetic.main.activity_book_activity.*
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.lang.Exception
import java.nio.file.Files
import java.nio.file.Paths
import java.util.ArrayList

class BookActivity : AppCompatActivity() {
    lateinit var binding: ActivityBookActivityBinding
    var st: Int = 6
    var end:Int = 7
    var pageNum = 0
    var max_end= 650
    val INC:Int = 6
    //SharedPreferences
    val APP_KEY: String  = "APP_P_KEY"
    val PAGE_KEY: String = "PAGE_K"
    lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookActivityBinding.inflate(layoutInflater)
        binding.tvTitle.text = intent.getStringExtra("bookTitle")
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(binding.root)

        page_num_tv.minValue = 1
        page_num_tv.maxValue = max_end
        page_num_tv.wrapSelectorWheel
        //SAVING THE DATA
        sharedPreferences = getSharedPreferences(APP_KEY, MODE_PRIVATE)
        page_num_tv.value = sharedPreferences.getInt(PAGE_KEY,1)
        pageNum = page_num_tv.value
        loadText(getString(intent.getIntExtra("bookText",R.string.bo_name)))
        //CHANGING THE PAGE
        page_num_tv.setOnValueChangedListener { numberPicker, i, i2 ->
            sharedPreferences.edit()
                .putInt(PAGE_KEY,i2)
                .apply()
            pageNum = sharedPreferences.getInt(PAGE_KEY,1)
            loadText(getString(intent.getIntExtra("bookText",R.string.bo_name)))
        }
    }
    private fun loadText(fileName:String) {
        try {

            var lines: List<String> = ArrayList<String>()
            var str :String = ""
            lines = assets.open(fileName).bufferedReader().use {
                it.readLines()}
            end = pageNum*INC
            st = end-INC
            page_num_tv.value = pageNum
            max_end =lines.size

            var i:Int = st
            while(i<end){
                str+=lines[i]
                    while(lines[i].isEmpty()||lines[i].length<150){
                        str+=lines[i]
                        i++
                    }
                i++
            }
            book_text.text = str

        }catch(e:Exception){
            Log.i("MyLog","ERROR")
            e.printStackTrace()
        }
    }
    fun onButtonLeft(view: View) {
        if(pageNum!=1){
            pageNum--
            loadText(getString(intent.getIntExtra("bookText",R.string.bo_name)));
        }
    }
    fun onButtonRight(view: View) {
        if(end+INC<=max_end){
            pageNum++
            loadText(getString(intent.getIntExtra("bookText",R.string.bo_name)));
        }else if(end!=max_end-1){
            st+=max_end-1-end;
            end=max_end-1;
            pageNum++
            loadText(getString(intent.getIntExtra("bookText",R.string.bo_name)));
        }
    }
}