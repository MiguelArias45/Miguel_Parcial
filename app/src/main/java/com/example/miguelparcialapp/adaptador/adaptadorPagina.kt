package com.example.miguelparcialapp.adaptador

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.miguelparcialapp.Datos.Viaje
import com.example.miguelparcialapp.R

class AdaptadorPagina(
    context: Context,
    private val viajes: List<Viaje>,
    private val onClick: (Viaje) -> Unit
) : ArrayAdapter<Viaje>(context, 0, viajes) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viaje = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_viaje, parent, false)

        val textViewDestino = view.findViewById<TextView>(R.id.text1)
        val textViewFecha = view.findViewById<TextView>(R.id.text2)

        textViewDestino.text = viaje?.destino
        textViewFecha.text = viaje?.fechaFin

        view.setOnClickListener {
            viaje?.let { onClick(it) }
        }

        return view
    }
}
