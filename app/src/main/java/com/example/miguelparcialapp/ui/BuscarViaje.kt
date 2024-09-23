package com.example.miguelparcialapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.miguelparcialapp.R
import com.example.miguelparcialapp.Datos.DatabaseHelper
import com.example.miguelparcialapp.Datos.Viaje
import com.example.miguelparcialapp.adaptador.AdaptadorPagina
import com.example.miguelparcialapp.ui.DetalleViajeFragment

class BuscarViajesFragment : Fragment() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var inputBusqueda: EditText
    private lateinit var btnBuscar: Button
    private lateinit var btnAgregar: Button
    private lateinit var listViewViajes: ListView
    private lateinit var viajeAdapter: AdaptadorPagina

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.buscar_viaje, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = DatabaseHelper(requireContext())

        inputBusqueda = view.findViewById(R.id.inputBusqueda)
        btnBuscar = view.findViewById(R.id.btnBuscar)
        btnAgregar = view.findViewById(R.id.btnAgregar)
        listViewViajes = view.findViewById(R.id.listViewViajes)

        btnBuscar.setOnClickListener {
            buscarViaje()
        }

        btnAgregar.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AgregarViajeFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun buscarViaje() {
        val destino = inputBusqueda.text.toString().trim()

        if (destino.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor, ingrese un destino.", Toast.LENGTH_SHORT).show()
            return
        }

        val viajes: List<Viaje> = dbHelper.getViajesPorDestino(destino)

        if (viajes.isNotEmpty()) {
            viajeAdapter = AdaptadorPagina(requireContext(), viajes) { viaje ->
                mostrarDetallesViaje(viaje)
            }
            listViewViajes.adapter = viajeAdapter

            listViewViajes.setOnItemClickListener { _, _, position, _ ->
                val viajeSeleccionado = viajes[position]
                mostrarDetallesViaje(viajeSeleccionado)
            }
        } else {
            Toast.makeText(requireContext(), "No se encontraron viajes para el destino ingresado.", Toast.LENGTH_SHORT).show()
            listViewViajes.adapter = null
        }
    }

    private fun mostrarDetallesViaje(viaje: Viaje) {
        val detalleViajeFragment = DetalleViajeFragment.newInstance(viaje)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, detalleViajeFragment)
            .addToBackStack(null)
            .commit()
    }
}
