package com.example.miguelparcialapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.miguelparcialapp.R
import com.example.miguelparcialapp.datos.DatabaseHelper
import com.example.miguelparcialapp.datos.Viaje

class AgregarViajeFragment : Fragment() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var inputDestino: EditText
    private lateinit var inputFechaInicio: EditText
    private lateinit var inputFechaFin: EditText
    private lateinit var inputActividades: EditText
    private lateinit var inputPresupuesto: EditText
    private lateinit var btnGuardar: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout para este fragmento
        return inflater.inflate(R.layout.agregar_viaje, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar la base de datos
        dbHelper = DatabaseHelper(requireContext())

        // Obtener referencias a las vistas
        inputDestino = view.findViewById(R.id.inputDestino)
        inputFechaInicio = view.findViewById(R.id.inputFechaInicio)
        inputFechaFin = view.findViewById(R.id.inputFechaFin)
        inputActividades = view.findViewById(R.id.inputActividades)
        inputPresupuesto = view.findViewById(R.id.inputPresupuesto)
        btnGuardar = view.findViewById(R.id.btnGuardar)

        // Configurar el botón de guardar
        btnGuardar.setOnClickListener {
            guardarViaje()
        }
    }

    private fun guardarViaje() {
        // Obtener los valores ingresados
        val destino = inputDestino.text.toString().trim()
        val fechaInicio = inputFechaInicio.text.toString().trim()
        val fechaFin = inputFechaFin.text.toString().trim()
        val actividades = inputActividades.text.toString().trim()
        val presupuesto = inputPresupuesto.text.toString().toDoubleOrNull() ?: 0.0

        // Validar los campos
        if (destino.isEmpty() || fechaInicio.isEmpty() || fechaFin.isEmpty() || actividades.isEmpty() || presupuesto <= 0) {
            Toast.makeText(requireContext(), "Por favor, complete todos los campos correctamente.", Toast.LENGTH_SHORT).show()
            return
        }

        // Crear un nuevo objeto Viaje
        val nuevoViaje = Viaje(destino = destino, fechaInicio = fechaInicio, fechaFin = fechaFin, actividades = actividades, presupuesto = presupuesto)

        // Guardar el viaje en la base de datos
        dbHelper.addViaje(nuevoViaje)
        Toast.makeText(requireContext(), "Viaje agregado con éxito.", Toast.LENGTH_SHORT).show()
        limpiarCampos()
    }

    private fun limpiarCampos() {
        // Limpiar los campos de entrada
        inputDestino.text.clear()
        inputFechaInicio.text.clear()
        inputFechaFin.text.clear()
        inputActividades.text.clear()
        inputPresupuesto.text.clear()
    }
}
