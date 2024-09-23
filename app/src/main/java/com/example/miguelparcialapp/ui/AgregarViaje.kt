package com.example.miguelparcialapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.miguelparcialapp.Datos.DatabaseHelper
import com.example.miguelparcialapp.Datos.Viaje
import com.example.miguelparcialapp.R

class AgregarViaje : Fragment() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.agregar_viaje, container, false)

        // Inicializar el helper de base de datos
        dbHelper = DatabaseHelper(requireContext())

        // Encontrar los elementos de entrada
        val destinoInput = view.findViewById<EditText>(R.id.destino_input)
        val fechaInicioInput = view.findViewById<EditText>(R.id.fecha_inicio_input)
        val fechaFinInput = view.findViewById<EditText>(R.id.fecha_fin_input)
        val actividadesInput = view.findViewById<EditText>(R.id.actividades_input)
        val agregarButton = view.findViewById<Button>(R.id.agregar_button)

        // Configurar el botón de agregar viaje
        agregarButton.setOnClickListener {
            // Obtener los valores de entrada
            val destino = destinoInput.text.toString()
            val fechaInicio = fechaInicioInput.text.toString()
            val fechaFin = fechaFinInput.text.toString()
            val actividades = actividadesInput.text.toString()

            // Verificar que los campos no estén vacíos
            if (destino.isNotEmpty() && fechaInicio.isNotEmpty() && fechaFin.isNotEmpty()) {
                // Crear un objeto Viaje
                val nuevoViaje = Viaje(
                    id = 0, // El ID se autoincrementa en la base de datos
                    destino = destino,
                    fechaInicio = fechaInicio,
                    fechaFin = fechaFin,
                    actividades = actividades,
                    presupuesto = 0.0 // Ajusta esto si quieres agregar un presupuesto
                )

                try {
                    // Insertar el viaje en la base de datos
                    dbHelper.addViaje(nuevoViaje)
                    Toast.makeText(requireContext(), "Viaje agregado exitosamente", Toast.LENGTH_SHORT).show()
                    // Navegar hacia atrás
                    requireActivity().supportFragmentManager.popBackStack()
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Error al agregar viaje: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Mostrar un mensaje de error si los campos están vacíos
                Toast.makeText(requireContext(), "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        return view // Retornar la vista
    }
}

