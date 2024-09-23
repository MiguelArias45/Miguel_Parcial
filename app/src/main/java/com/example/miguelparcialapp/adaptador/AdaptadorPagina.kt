package com.example.miguelparcialapp.adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.miguelparcialapp.Datos.Viaje
import com.example.miguelparcialapp.R

class AdaptadorPagina(private val viajes: List<Viaje>, private val onClick: (Viaje) -> Unit) :
    RecyclerView.Adapter<AdaptadorPagina.ViajeViewHolder>() {

    class ViajeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textDestino: TextView = itemView.findViewById(R.id.textDestino)
        val textFechaInicio: TextView = itemView.findViewById(R.id.textFechaInicio)

        fun bind(viaje: Viaje, onClick: (Viaje) -> Unit) {
            textDestino.text = viaje.destino
            textFechaInicio.text = viaje.fechaInicio
            itemView.setOnClickListener { onClick(viaje) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViajeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_viaje, parent, false)
        return ViajeViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViajeViewHolder, position: Int) {
        holder.bind(viajes[position], onClick)
    }

    override fun getItemCount(): Int = viajes.size
}


