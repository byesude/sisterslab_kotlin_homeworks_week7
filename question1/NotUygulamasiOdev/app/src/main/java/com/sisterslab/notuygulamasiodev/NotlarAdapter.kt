package com.sisterslab.notuygulamasiodev

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotlarAdapter(private val mContext : Context, private val notlarListe : List<Notlar>)
    : RecyclerView.Adapter<NotlarAdapter.CardTasarimTutucu>() {

    inner class CardTasarimTutucu(tasarim : View) : RecyclerView.ViewHolder(tasarim){
        var textViewDers : TextView
        var textViewVize : TextView
        var textViewFinal : TextView
        var imageViewOk : ImageView

        init {
            textViewDers = tasarim.findViewById(R.id.textViewDers)
            textViewVize = tasarim.findViewById(R.id.textViewVize)
            textViewFinal = tasarim.findViewById(R.id.textViewFinal)
            imageViewOk = tasarim.findViewById(R.id.imageViewOk)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTasarimTutucu {
        val tasarim = LayoutInflater.from(mContext).inflate(R.layout.card_tasarim, parent, false)
        return CardTasarimTutucu(tasarim)
    }

    override fun getItemCount(): Int {
        return notlarListe.size
    }

    override fun onBindViewHolder(holder: CardTasarimTutucu, position: Int) {
        val not = notlarListe.get(position)

        holder.textViewDers.text = not.ders_adi
        holder.textViewVize.text = not.vize_notu.toString()
        holder.textViewFinal.text = not.final_notu.toString()

        holder.imageViewOk.setOnClickListener{

            val intent = Intent(mContext, GuncelleActivity::class.java)
            intent.putExtra("nesne", not)
            mContext.startActivity(intent)

        }
    }
}