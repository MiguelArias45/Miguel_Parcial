package com.example.miguelparcialapp.Datos

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

// Clase para manejar la base de datos SQLite
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "viajes.db" // Nombre de la base de datos
        private const val DATABASE_VERSION = 1 // Versión de la base de datos

        // Tabla de viajes y sus columnas
        const val TABLE_VIAJES = "viajes"
        const val COLUMN_ID = "id"
        const val COLUMN_DESTINO = "destino"
        const val COLUMN_FECHA_INICIO = "fecha_inicio"
        const val COLUMN_FECHA_FIN = "fecha_fin"
        const val COLUMN_ACTIVIDADES = "actividades"
        const val COLUMN_PRESUPUESTO = "presupuesto"
    }

    // Se llama cuando se crea la base de datos
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_VIAJES (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_DESTINO TEXT," +
                "$COLUMN_FECHA_INICIO TEXT," +
                "$COLUMN_FECHA_FIN TEXT," +
                "$COLUMN_ACTIVIDADES TEXT," +
                "$COLUMN_PRESUPUESTO REAL)")
        db.execSQL(createTable) // Ejecutar la creación de la tabla
    }

    // Se llama cuando se actualiza la base de datos
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_VIAJES") // Eliminar la tabla existente
        onCreate(db) // Crear la nueva tabla
    }

    // Método para agregar un viaje a la base de datos
    fun addViaje(viaje: Viaje) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_DESTINO, viaje.destino)
            put(COLUMN_FECHA_INICIO, viaje.fechaInicio)
            put(COLUMN_FECHA_FIN, viaje.fechaFin)
            put(COLUMN_ACTIVIDADES, viaje.actividades)
            put(COLUMN_PRESUPUESTO, viaje.presupuesto)
        }
        db.insert(TABLE_VIAJES, null, values) // Insertar el nuevo viaje
        db.close() // Cerrar la base de datos
    }

    // Método para actualizar un viaje existente
    fun updateViaje(viaje: Viaje) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_DESTINO, viaje.destino)
            put(COLUMN_FECHA_INICIO, viaje.fechaInicio)
            put(COLUMN_FECHA_FIN, viaje.fechaFin)
            put(COLUMN_ACTIVIDADES, viaje.actividades)
            put(COLUMN_PRESUPUESTO, viaje.presupuesto)
        }
        db.update(TABLE_VIAJES, values, "$COLUMN_ID=?", arrayOf(viaje.id.toString())) // Actualizar el viaje
        db.close() // Cerrar la base de datos
    }

    // Método para eliminar un viaje de la base de datos
    fun deleteViaje(id: Long) {
        val db = this.writableDatabase
        db.delete(TABLE_VIAJES, "$COLUMN_ID=?", arrayOf(id.toString())) // Eliminar el viaje por ID
        db.close() // Cerrar la base de datos
    }

    // Método para obtener todos los viajes
    fun getAllViajes(): List<Viaje> {
        val viajesList = mutableListOf<Viaje>() // Lista para almacenar los viajes
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_VIAJES", null) // Consulta para obtener todos los viajes

        try {
            if (cursor.moveToFirst()) {
                do {
                    val viaje = Viaje(
                        id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        destino = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESTINO)) ?: "",
                        fechaInicio = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FECHA_INICIO)) ?: "",
                        fechaFin = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FECHA_FIN)) ?: "",
                        actividades = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ACTIVIDADES)) ?: "",
                        presupuesto = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRESUPUESTO))
                    )
                    viajesList.add(viaje) // Agregar el viaje a la lista
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error fetching viajes: ${e.message}") // Manejo de errores
        } finally {
            cursor.close() // Cerrar el cursor
        }
        return viajesList // Retornar la lista de viajes
    }

    // Método para obtener viajes por destino
    fun getViajesPorDestino(destino: String): List<Viaje> {
        val viajes = mutableListOf<Viaje>()
        val db = this.readableDatabase
        val cursor: Cursor = db.query(
            TABLE_VIAJES,
            null,
            "$COLUMN_DESTINO LIKE ?",
            arrayOf("%$destino%"),
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
                val destino = cursor.getString(cursor.getColumnIndex(COLUMN_DESTINO))
                val fechaInicio = cursor.getString(cursor.getColumnIndex(COLUMN_FECHA_INICIO))
                val fechaFin = cursor.getString(cursor.getColumnIndex(COLUMN_FECHA_FIN))
                val actividades = cursor.getString(cursor.getColumnIndex(COLUMN_ACTIVIDADES))
                val presupuesto = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRESUPUESTO))

                val viaje = Viaje(id, destino, fechaInicio, fechaFin, actividades, presupuesto)
                viajes.add(viaje)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return viajes
    }
}
