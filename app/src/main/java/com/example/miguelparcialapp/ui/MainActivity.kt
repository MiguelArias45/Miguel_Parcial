package com.example.miguelparcialapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.miguelparcialapp.R
import com.example.miguelparcialapp.data.DatabaseHelper
import com.example.miguelparcialapp.data.Viaje
import com.example.miguelparcialapp.ui.adapters.ViajeAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var viajeAdapter: ViajeAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this) // Inicializamos la base de datos
        recyclerView = findViewById(R.id.recyclerViewViajes) // Referencia al RecyclerView

        // Configuramos el RecyclerView
        viajeAdapter = ViajeAdapter(dbHelper.getAllViajes()) { viaje ->
            mostrarDetallesViaje(viaje)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = viajeAdapter

        // Botón para agregar un viaje
        btnAgregarViaje.setOnClickListener {
            val intent = Intent(this, AgregarViajeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Actualiza la lista de viajes al regresar a la actividad
        viajeAdapter.updateViajes(dbHelper.getAllViajes())
    }

    private fun mostrarDetallesViaje(viaje: Viaje) {
        // Redirigir a DetalleViajeFragment pasando el viaje seleccionado
        val detalleViajeFragment = DetalleViajeFragment.newInstance(viaje)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, detalleViajeFragment)
            .addToBackStack(null)
            .commit()
    }
}
