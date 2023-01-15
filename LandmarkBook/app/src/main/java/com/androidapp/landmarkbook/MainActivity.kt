package com.androidapp.landmarkbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidapp.landmarkbook.databinding.ActivityDetailsBinding
import com.androidapp.landmarkbook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var landmarkList : ArrayList<Landmark>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        landmarkList = ArrayList<Landmark>()

        val ayasofya = Landmark("Ayasofya","Türkiye",R.drawable.aysfycm)
        val kubbetulsahra = Landmark("Kublet'ül Sahra", "Filistin",R.drawable.kbtshr)
        val pisa = Landmark("Pisa","İtalya",R.drawable.pisa)
        val eyfel = Landmark("Eyfel","Fransa",R.drawable.eyfel)

        landmarkList.add(ayasofya)
        landmarkList.add(kubbetulsahra)
        landmarkList.add(pisa)
        landmarkList.add(eyfel)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val landmarkAdapter = LandmarkAdapter(landmarkList)
        binding.recyclerView.adapter = landmarkAdapter
        /*
        //Adapter:Layout & Data
        //Mapping
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,landmarkList.map { landmark -> landmark.name })
        binding.listView.adapter = adapter
        binding.listView.onItemClickListener = AdapterView.OnItemClickListener{parent, view, position, id ->
            val intent = Intent(MainActivity@this,DetailsActivity::class.java)
            intent.putExtra("landmark",landmarkList.get(position))
            startActivity(intent)
        }
         */


    }
}