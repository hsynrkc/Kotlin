package com.androidapp.sozlukuygulamasi

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView



class KelimelerAdapter(private val mContext : Context, private val kelimelerListe : List<Kelimeler>)
    : RecyclerView.Adapter<KelimelerAdapter.CardTasarimTutucu>() {

    //cardtasarimtutucu sınıfını kelimeleradapter sınıfına bağladık

    inner class CardTasarimTutucu(tasarim : View) : RecyclerView.ViewHolder(tasarim){  //görsel nesnelerimizin olduğu sınıf
        var kelime_card : CardView
        var textViewIngilizce : TextView
        var textViewTurkce : TextView

        init {
            kelime_card = tasarim.findViewById(R.id.kelime_card)
            textViewIngilizce = tasarim.findViewById(R.id.textViewIngilizce)
            textViewTurkce = tasarim.findViewById(R.id.textViewTurkce)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTasarimTutucu {
        val tasarim = LayoutInflater.from(mContext).inflate(R.layout.card_tasarim,parent,false) //yazılımsal tasarımı görsel tasarıma bağlıyoruz(parent), false = başka bir tasarım bağlamayacağız demek.
        return CardTasarimTutucu(tasarim)
    }

    override fun onBindViewHolder(holder: CardTasarimTutucu, position: Int) {
        //bize gelen veri kümesini burada işleyeceğiz, hangi alana hangi veri aktarılacak, hangi görsel nesne hangi işlevi yapacak bunlar belirtilecek

        val kelime =kelimelerListe.get(position) //veri kümesi içerisindeki kelimeler gelecek ve kelime değişkenine yerleşecektir

        holder.textViewIngilizce.text = kelime.ingilizce
        holder.textViewTurkce.text = kelime.turkce

        holder.kelime_card.setOnClickListener {
            val intent = Intent(mContext,DetayActivity::class.java)
            intent.putExtra("nesne",kelime)

            mContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return kelimelerListe.size //veri kümesi boyutu
    }
}