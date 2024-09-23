package com.example.miguelparcialapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.miguelparcialapp.R
import com.example.miguelparcialapp.data.Viaje

class ViajeAdapter(private var viajes: List<Viaje>, private val onClick: (Viaje) -> Unit) :
    RecyclerView.Adapter<ViajeAdapter.ViajeViewHolder>() {

    class ViajeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewDestino: TextView = view.findViewById(R.id.textViewDestino)
        val textViewFecha: TextView = view.findViewById(R.id.textViewFecha)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViajeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_viaje, parent, false)
        return ViajeViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViajeViewHolder, position: Int) {
        val viaje = viajes[position]
        holder.textViewDestino.text = viaje.destino
        holder.textViewFecha.text = "${viaje.fechaInicio} - ${viaje.fechaFin}"

        holder.itemView.setOnClickListener { onClick(viaje) }
    }

    override fun getItemCount(): Int = viajes.size

    fun updateViajes(newViajes: List<Viaje>) {
        viajes = newViajes
        notifyDataSetChanged() // Notifica al adaptador que los datos han cambiado
    }
}



