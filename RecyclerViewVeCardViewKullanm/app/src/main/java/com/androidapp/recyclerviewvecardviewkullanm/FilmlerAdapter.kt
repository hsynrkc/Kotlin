package com.androidapp.recyclerviewvecardviewkullanm

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class FilmlerAdapter(private var mContext:Context, private var filmListesi:List<Filmler>)
    : RecyclerView.Adapter<FilmlerAdapter.CardTasarimNesneleri>() {

    inner class CardTasarimNesneleri(view:View):RecyclerView.ViewHolder(view){
        var imageView : ImageView
        var textViewName: TextView
        var textViewFiyat: TextView
        var buttonEkle: Button

        init {
            imageView = view.findViewById(R.id.imageView)
            textViewName = view.findViewById(R.id.textViewName)
            textViewFiyat = view.findViewById(R.id.textViewFiyat)
            buttonEkle = view.findViewById(R.id.buttonEkle)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTasarimNesneleri {
        val tasarim = LayoutInflater.from(mContext).inflate(R.layout.cardview_film_tasarim,parent,false)

        return CardTasarimNesneleri(tasarim)
    }

    override fun onBindViewHolder(holder: CardTasarimNesneleri, position: Int) {
        val film = filmListesi[position]

        holder.textViewName.text = film.film_ad
        holder.textViewFiyat.text = "${film.film_fiyat} TL"
        holder.imageView.setImageResource(
            mContext.resources.getIdentifier(film.film_resim_ad,"drawable",mContext.packageName))

        holder.buttonEkle.setOnClickListener {
            Snackbar.make(it,"${film.film_ad} Sepete Eklendi!",Snackbar.LENGTH_SHORT).show()
            //Toast.makeText(mContext, "${film.film_ad} Sepete Eklendi!",Toast.LENGTH_SHORT).show()


        }
    }

    override fun getItemCount(): Int {
        return filmListesi.size
    }

}