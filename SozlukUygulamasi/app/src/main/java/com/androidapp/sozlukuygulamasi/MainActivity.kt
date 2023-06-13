package com.androidapp.sozlukuygulamasi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidapp.sozlukuygulamasi.databinding.ActivityMainBinding
import com.info.sqlitekullanimihazirveritabani.DatabaseCopyHelper

class MainActivity : AppCompatActivity(),SearchView.OnQueryTextListener {
    private lateinit var binding :ActivityMainBinding
    private lateinit var kelimelerListe : ArrayList<Kelimeler>
    private lateinit var adapter: KelimelerAdapter
    private lateinit var vt : VeriTabaniYardimcisi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        veritabaniKopyala()

        binding.toolbar.title = "Sözlük Uygulaması"
        setSupportActionBar(binding.toolbar)

        binding.rv.setHasFixedSize(true)
        binding.rv.layoutManager = LinearLayoutManager(this)

        /*kelimelerListe = ArrayList()

        val k1 = Kelimeler(1,"Dog","Köpek")
        val k2 = Kelimeler(2,"Cat","Kedi")
        val k3 = Kelimeler(3,"Duck","Ördek")

        kelimelerListe.add(k1)
        kelimelerListe.add(k2)
        kelimelerListe.add(k3)*/

        vt = VeriTabaniYardimcisi(this)
        kelimelerListe = Kelimelerdao().tumKelimeler(vt)

        adapter = KelimelerAdapter(this,kelimelerListe)

        binding.rv.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_toolbar,menu)
        val item = menu?.findItem(R.id.action_ara)
        val search = item?.actionView as SearchView
        search.setOnQueryTextListener(this)

        return super.onCreateOptionsMenu(menu)
    }

    fun veritabaniKopyala(){
        val copyHelper = DatabaseCopyHelper(this)
        try {
            copyHelper.createDataBase()
            copyHelper.openDataBase()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun arama(kelimeAra:String){

        kelimelerListe = Kelimelerdao().aramaYap(vt,kelimeAra)

        adapter = KelimelerAdapter(this,kelimelerListe)

        binding.rv.adapter = adapter
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            arama(query)
        }
        //Log.e("Gönderilen Arama", query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            arama(newText)
        }
        //Log.e("Harf girdikçe",newText)
        return true
    }

}