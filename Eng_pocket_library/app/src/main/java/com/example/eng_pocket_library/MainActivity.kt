package com.example.eng_pocket_library

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.eng_pocket_library.fragments.BookFragment
import com.example.eng_pocket_library.fragments.NotebookFragment
import com.example.eng_pocket_library.fragments.TranslatorFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nav_view.setNavigationItemSelectedListener(this)
        onStartReplacement(BookFragment())
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.books_item -> onStartReplacement(BookFragment())
            R.id.translator_item -> onStartReplacement(TranslatorFragment())
            R.id.notebook_item -> onStartReplacement(NotebookFragment())
            R.id.settings_item -> Toast.makeText(this,R.string.menu_settings,Toast.LENGTH_LONG).show()


        }
        return true
    }
    fun onStartReplacement(fragment:Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame_layout,fragment)
            .commit()
    }

}