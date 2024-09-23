package com.example.miguelparcialapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.miguelparcialapp.R
import com.example.miguelparcialapp.Datos.DatabaseHelper
import com.example.miguelparcialapp.Datos.Viaje
import com.example.miguelparcialapp.adaptador.AdaptadorPagina
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var viajeAdapter: AdaptadorPagina
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAgregarViaje: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializamos las vistas
        recyclerView = findViewById(R.id.recyclerViewViajes)
        btnAgregarViaje = findViewById(R.id.btnAgregarViaje)

        // Inicializamos la base de datos
        dbHelper = DatabaseHelper(this)

        // Configuramos el RecyclerView y el adaptador
        viajeAdapter = AdaptadorPagina(dbHelper.getAllViajes()) { viaje ->
            mostrarDetallesViaje(viaje)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = viajeAdapter

        // Botón para agregar un viaje
        btnAgregarViaje.setOnClickListener {
            val intent = Intent(this, AgregarViaje::class.java) // Asegúrate de que este nombre sea correcto
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Actualiza la lista de viajes al regresar a la actividad
        viajeAdapter = AdaptadorPagina(dbHelper.getAllViajes()) { viaje ->
            mostrarDetallesViaje(viaje)
        }
        recyclerView.adapter = viajeAdapter // Reasignamos el adaptador con la nueva lista
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
