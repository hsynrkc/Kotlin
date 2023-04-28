package com.androidapp.recyclerviewvecardviewkullanm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.androidapp.recyclerviewvecardviewkullanm.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var filmlerArrayList: ArrayList<Filmler>
    private lateinit var adapter: FilmlerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rv.setHasFixedSize(true)
        binding.rv.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)

        val f1 = Filmler(1,"Avengers","avengers",35.99)
        val f2 = Filmler(2,"A Beautiful Mind","a_beautiful_mind",25.99)
        val f3 = Filmler(3,"Inception","inception",30.99)
        val f4 = Filmler(4,"John Wick","john_wick",32.99)
        val f5 = Filmler(5,"The Other Son","the_other_son",23.99)

        filmlerArrayList = ArrayList<Filmler>()
        filmlerArrayList.add(f1)
        filmlerArrayList.add(f2)
        filmlerArrayList.add(f3)
        filmlerArrayList.add(f4)
        filmlerArrayList.add(f5)

        adapter = FilmlerAdapter(this,filmlerArrayList)
        binding.rv.adapter = adapter
    }
}