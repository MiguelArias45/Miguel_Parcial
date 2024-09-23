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
import com.example.miguelparcialapp.data.DatabaseHelper
import com.example.miguelparcialapp.data.Viaje
import java.text.SimpleDateFormat
import java.util.*

class EditarViajeFragment : Fragment() {

    private lateinit var db: DatabaseHelper
    private var viajeId: Long? = null

    // EditTexts para editar los detalles del viaje
    private lateinit var inputDestino: EditText
    private lateinit var inputFechaInicio: EditText
    private lateinit var inputFechaFin: EditText
    private lateinit var inputActividades: EditText
    private lateinit var inputPresupuesto: EditText
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viajeId = arguments?.getLong("viajeId") // Obtener el ID del viaje desde los argumentos
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout para este fragmento
        val view = inflater.inflate(R.layout.fragment_editar_viaje, container, false)

        // Inicializar la base de datos
        db = DatabaseHelper(requireContext())

        // Referencias a los elementos del layout
        inputDestino = view.findViewById(R.id.inputDestino)
        inputFechaInicio = view.findViewById(R.id.inputFechaInicio)
        inputFechaFin = view.findViewById(R.id.inputFechaFin)
        inputActividades = view.findViewById(R.id.inputActividades)
        inputPresupuesto = view.findViewById(R.id.inputPresupuesto)
        btnGuardar = view.findViewById(R.id.btnGuardar)

        // Cargar los detalles del viaje para edición
        viajeId?.let { cargarDetallesViaje(it) }

        // Configurar el botón de guardar
        btnGuardar.setOnClickListener {
            viajeId?.let { id -> guardarViaje(id) }
        }

        return view
    }

    // Función para cargar los detalles del viaje desde la base de datos
    private fun cargarDetallesViaje(id: Long) {
        val viaje = db.obtenerViaje(id)
        viaje?.let {
            inputDestino.setText(it.destino)
            inputFechaInicio.setText(it.fechaInicio)
            inputFechaFin.setText(it.fechaFin)
            inputActividades.setText(it.actividades)
            inputPresupuesto.setText(it.presupuesto.toString())
        }
    }

    // Función para guardar los cambios en el viaje
    private fun guardarViaje(id: Long) {
        val destino = inputDestino.text.toString()
        val fechaInicio = inputFechaInicio.text.toString()
        val fechaFin = inputFechaFin.text.toString()
        val actividades = inputActividades.text.toString()
        val presupuesto = inputPresupuesto.text.toString().toDoubleOrNull()

        // Validar campos antes de guardar
        if (destino.isEmpty() || fechaInicio.isEmpty() || fechaFin.isEmpty() || actividades.isEmpty() || presupuesto == null || presupuesto < 0) {
            Toast.makeText(requireContext(), "Por favor, complete todos los campos correctamente.", Toast.LENGTH_SHORT).show()
            return
        }

        // Validar que la fecha de inicio sea anterior a la fecha de fin
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        try {
            val inicio = sdf.parse(fechaInicio)
            val fin = sdf.parse(fechaFin)
            if (inicio.after(fin)) {
                Toast.makeText(requireContext(), "La fecha de inicio debe ser anterior a la fecha de fin.", Toast.LENGTH_SHORT).show()
                return
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Formato de fecha no válido.", Toast.LENGTH_SHORT).show()
            return
        }

        val viaje = Viaje(id, destino, fechaInicio, fechaFin, actividades, presupuesto)
        db.actualizarViaje(viaje)

        Toast.makeText(requireContext(), "Viaje actualizado exitosamente.", Toast.LENGTH_SHORT).show()
        // Volver al fragmento de detalles de viaje
        parentFragmentManager.popBackStack()
    }
}


