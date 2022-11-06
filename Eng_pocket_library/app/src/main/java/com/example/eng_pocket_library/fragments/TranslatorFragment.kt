package com.example.eng_pocket_library.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eng_pocket_library.R
import com.example.eng_pocket_library.databinding.FragmentTranslatorBinding
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.android.synthetic.main.fragment_translator.*


class TranslatorFragment : Fragment() {
    lateinit var binding:FragmentTranslatorBinding
    lateinit var englishRussianTranslator : Translator
    var requestStr :String = "";

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTranslatorBinding.inflate(inflater)
        binding.progressBar.visibility = View.INVISIBLE

         binding.onTranslateButton.setOnClickListener {
            requestStr = requestTv.text.toString()
             prepareTranslateModel(requestStr)
             binding.progressBar.visibility = View.VISIBLE
        }

        return binding.root
    }

    private fun prepareTranslateModel(s:String){
        val options:TranslatorOptions = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.RUSSIAN)
            .build()
        englishRussianTranslator = Translation.getClient(options)
        //Adding a model
        englishRussianTranslator.downloadModelIfNeeded().addOnSuccessListener {

            onTranslate(requestStr)

        }.addOnFailureListener {
            binding.resultTv.text = "FAIL : ${it.message}"
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() {
        }
    }
    private fun onTranslate(s:String){
        englishRussianTranslator.translate(s).addOnSuccessListener {
            binding.progressBar.visibility = View.INVISIBLE
            binding.resultTv.text= it
        }.addOnFailureListener {

            binding.resultTv.text ="ERROR: ${it.message}"
        }

    }
}

