package com.example.miguelparcialapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.miguelparcialapp.Datos.Viaje
import com.example.miguelparcialapp.R

class DetalleViajeFragment : Fragment() {

    private lateinit var viaje: Viaje

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detalle_viaje, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener el viaje pasado como argumento
        viaje = arguments?.getParcelable(ARG_VIAJE) ?: throw IllegalArgumentException("Viaje no encontrado")

        val textViewDestino = view.findViewById<TextView>(R.id.textViewDestino)
        val textViewFechaInicio = view.findViewById<TextView>(R.id.textViewFechaInicio)
        val textViewFechaFin = view.findViewById<TextView>(R.id.textViewFechaFin)

        // Mostrar la información del viaje
        textViewDestino.text = viaje.destino
        textViewFechaInicio.text = viaje.fechaInicio
        textViewFechaFin.text = viaje.fechaFin
    }

    companion object {
        private const val ARG_VIAJE = "viaje"

        fun newInstance(viaje: Viaje): DetalleViajeFragment {
            val fragment = DetalleViajeFragment()
            val args = Bundle()
            args.putParcelable(ARG_VIAJE, viaje) // Asegúrate de que Viaje implemente Parcelable
            fragment.arguments = args
            return fragment
        }
    }
}

