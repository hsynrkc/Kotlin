package com.androidapp.bayrakquizuygulamasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androidapp.bayrakquizuygulamasi.databinding.ActivityMainBinding
import com.info.sqlitekullanimihazirveritabani.DatabaseCopyHelper

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        veritabaniKopyala()

        binding.buttonBasla.setOnClickListener {
            startActivity(Intent(this@MainActivity,QuizActivity::class.java))
        }

    }

    fun veritabaniKopyala(){
        val copyHelper = DatabaseCopyHelper(this)
        try {
            copyHelper.createDataBase()

        }catch (e:Exception){
            e.printStackTrace()
        }
        try {
            copyHelper.openDataBase()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}