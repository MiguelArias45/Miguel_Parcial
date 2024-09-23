package com.example.miguelparcialapp.datos

// Clase que representa un viaje
data class Viaje(
    val id: Int = 0, // ID del viaje (se inicializa en 0 para viajes nuevos)
    val destino: String, // Destino del viaje
    val fechaInicio: String, // Fecha de inicio del viaje
    val fechaFin: String, // Fecha de fin del viaje
    val actividades: String, // Actividades planificadas para el viaje
    val presupuesto: Double // Presupuesto del viaje
) {
    // Función para obtener un resumen del viaje
    fun getResumen(): String {
        return """
            |Destino: $destino
            |Fecha Inicio: $fechaInicio
            |Fecha Fin: $fechaFin
            |Actividades: $actividades
            |Presupuesto: $presupuesto
        """.trimMargin()
    }
}

