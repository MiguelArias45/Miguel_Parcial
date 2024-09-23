package com.example.miguelparcialapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.miguelparcialapp.Datos.Viaje
import com.example.miguelparcialapp.R

class EditarViaje : Fragment() {

    private lateinit var editDestino: EditText
    private lateinit var editFechaInicio: EditText
    private lateinit var editFechaFin: EditText
    private lateinit var editActividades: EditText
    private lateinit var editPresupuesto: EditText
    private lateinit var btnGuardar: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.editar_viaje, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editDestino = view.findViewById(R.id.inputDestino)
        editFechaInicio = view.findViewById(R.id.inputFechaInicio)
        editFechaFin = view.findViewById(R.id.inputFechaFin)
        editActividades = view.findViewById(R.id.inputActividades)
        editPresupuesto = view.findViewById(R.id.inputPresupuesto)
        btnGuardar = view.findViewById(R.id.btnGuardar)

        obtenerViaje()

        btnGuardar.setOnClickListener {
            actualizarViaje()
        }
    }

    private fun obtenerViaje() {
        arguments?.let {
            val viaje = it.getParcelable<Viaje>("viaje")
            viaje?.let { v ->
                editDestino.setText(v.destino)
                editFechaInicio.setText(v.fechaInicio)
                editFechaFin.setText(v.fechaFin)
                editActividades.setText(v.actividades)
                editPresupuesto.setText(v.presupuesto.toString())
            }
        }
    }

    private fun actualizarViaje() {
        val destino = editDestino.text.toString()
        val fechaInicio = editFechaInicio.text.toString()
        val fechaFin = editFechaFin.text.toString()
        val actividades = editActividades.text.toString()
        val presupuesto = editPresupuesto.text.toString().toDoubleOrNull()

        if (presupuesto != null) {
            // Aquí deberías actualizar el viaje en tu base de datos o lista
            Toast.makeText(context, "Viaje actualizado", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Presupuesto inválido", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun newInstance(viaje: Viaje): EditarViaje {
            val fragment = EditarViaje()
            val args = Bundle()
            args.putParcelable("viaje", viaje)
            fragment.arguments = args
            return fragment
        }
    }
}
