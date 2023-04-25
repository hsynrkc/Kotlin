package com.androidapp.sayitahminuygulamasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_tahmin.*
import kotlin.random.Random

class TahminActivity : AppCompatActivity() {

    private var rastgeleSayi=0
    private var sayac =5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tahmin)

        rastgeleSayi = Random.nextInt(101)
        Log.e("Rastgele Sayı",rastgeleSayi.toString())

        buttonTahmin.setOnClickListener {

            sayac = sayac - 1

            val tahmin = editTextGirdi.text.toString().toInt()
            if(tahmin == rastgeleSayi){
                val intent = Intent(this@TahminActivity,SonucActivity::class.java)

                intent.putExtra("sonuc",true)

                finish()
                startActivity(intent)
                return@setOnClickListener
            }

            if (tahmin<rastgeleSayi){
                textViewYardim.text = "ARTTIR"
                textViewKalanHak.text = "Kalan Hak : $sayac"
            }
            if (tahmin>rastgeleSayi){
                textViewYardim.text = "AZALT"
                textViewKalanHak.text = "Kalan Hak : $sayac"
            }
            if (sayac == 0){
                val intent = Intent(this@TahminActivity,SonucActivity::class.java)

                intent.putExtra("sonuc",false)

                finish()
                startActivity(intent)
            }

            editTextGirdi.setText("")


        }
    }
}