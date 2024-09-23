package com.example.miguelparcialapp.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.miguelparcialapp.R
import com.example.miguelparcialapp.data.DatabaseHelper
import com.example.miguelparcialapp.data.Viaje

class DetalleViajeFragment : Fragment() {

    private lateinit var db: DatabaseHelper
    private lateinit var viaje: Viaje

    private lateinit var textViewDestino: TextView
    private lateinit var textViewFechaInicio: TextView
    private lateinit var textViewFechaFin: TextView
    private lateinit var textViewActividades: TextView
    private lateinit var textViewPresupuesto: TextView
    private lateinit var btnEditar: Button
    private lateinit var btnBorrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viaje = arguments?.getParcelable("viaje") ?: throw IllegalArgumentException("Viaje no encontrado")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detalle_viaje, container, false)

        db = DatabaseHelper(requireContext())

        textViewDestino = view.findViewById(R.id.textViewDestino)
        textViewFechaInicio = view.findViewById(R.id.textViewFechaInicio)
        textViewFechaFin = view.findViewById(R.id.textViewFechaFin)
        textViewActividades = view.findViewById(R.id.textViewActividades)
        textViewPresupuesto = view.findViewById(R.id.textViewPresupuesto)
        btnEditar = view.findViewById(R.id.btnEditar)
        btnBorrar = view.findViewById(R.id.btnBorrar)

        mostrarDetallesViaje()

        btnEditar.setOnClickListener {
            editarViaje()
        }

        btnBorrar.setOnClickListener {
            borrarViaje()
        }

        return view
    }

    private fun mostrarDetallesViaje() {
        textViewDestino.text = viaje.destino
        textViewFechaInicio.text = viaje.fechaInicio
        textViewFechaFin.text = viaje.fechaFin
        textViewActividades.text = viaje.actividades
        textViewPresupuesto.text = getString(R.string.formato_presupuesto, viaje.presupuesto)
    }

    private fun editarViaje() {
        val fragment = EditarViajeFragment()
        val bundle = Bundle()
        bundle.putParcelable("viaje", viaje)
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun borrarViaje() {
        val confirmDialog = AlertDialog.Builder(requireContext())
        confirmDialog.setTitle("Eliminar viaje")
        confirmDialog.setMessage("¿Está seguro de que desea eliminar este viaje?")
        confirmDialog.setPositiveButton("Sí") { _, _ ->
            db.borrarViaje(viaje.id)
            Toast.makeText(requireContext(), "Viaje eliminado", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        }
        confirmDialog.setNegativeButton("No", null)
        confirmDialog.show()
    }
}
