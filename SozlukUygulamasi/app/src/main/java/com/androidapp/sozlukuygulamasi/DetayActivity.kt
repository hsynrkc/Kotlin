package com.androidapp.sozlukuygulamasi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androidapp.sozlukuygulamasi.databinding.ActivityDetayBinding
import com.androidapp.sozlukuygulamasi.databinding.ActivityMainBinding

class DetayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val kelime = intent.getSerializableExtra("nesne") as Kelimeler

        binding.textViewIng.text = kelime.ingilizce
        binding.textViewTurk.text = kelime.turkce



    }
}