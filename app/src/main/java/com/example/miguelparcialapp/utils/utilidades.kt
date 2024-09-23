package com.example.miguelparcialapp.utils

import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

object Utilidades {

    // Función para mostrar mensajes cortos en la pantalla
    fun mostrarToast(context: Context, mensaje: String) {
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
    }

    // Función para formatear fechas
    fun formatearFecha(fecha: Date, formato: String = "dd/MM/yyyy"): String {
        val sdf = SimpleDateFormat(formato, Locale.getDefault())
        return sdf.format(fecha)
    }

    // Función para validar que un campo no esté vacío
    fun esCampoValido(campo: String): Boolean {
        return campo.isNotEmpty()
    }

    // Otras utilidades que puedas necesitar...
}
