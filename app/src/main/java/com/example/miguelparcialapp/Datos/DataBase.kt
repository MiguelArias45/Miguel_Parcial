package com.example.miguelparcialapp.Datos

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Clase para manejar la base de datos SQLite
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "viajes.db" // Nombre de la base de datos
        private const val DATABASE_VERSION = 1 // Versión de la base de datos

        // Tabla de viajes
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
        val createTable = ("CREATE TABLE $TABLE_VIAJES ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_DESTINO TEXT,"
                + "$COLUMN_FECHA_INICIO TEXT,"
                + "$COLUMN_FECHA_FIN TEXT,"
                + "$COLUMN_ACTIVIDADES TEXT,"
                + "$COLUMN_PRESUPUESTO REAL)")
        db.execSQL(createTable)
    }

    // Se llama cuando se actualiza la base de datos
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_VIAJES")
        onCreate(db)
    }

    // Método para agregar un viaje
    fun addViaje(viaje: Viaje) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_DESTINO, viaje.destino)
            put(COLUMN_FECHA_INICIO, viaje.fechaInicio)
            put(COLUMN_FECHA_FIN, viaje.fechaFin)
            put(COLUMN_ACTIVIDADES, viaje.actividades)
            put(COLUMN_PRESUPUESTO, viaje.presupuesto)
        }
        db.insert(TABLE_VIAJES, null, values)
        db.close()
    }

    // Método para obtener todos los viajes
    fun getAllViajes(): List<Viaje> {
        val viajesList = mutableListOf<Viaje>()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_VIAJES", null)

        if (cursor.moveToFirst()) {
            do {
                val viaje = Viaje(
                    id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                    destino = cursor.getString(cursor.getColumnIndex(COLUMN_DESTINO)),
                    fechaInicio = cursor.getString(cursor.getColumnIndex(COLUMN_FECHA_INICIO)),
                    fechaFin = cursor.getString(cursor.getColumnIndex(COLUMN_FECHA_FIN)),
                    actividades = cursor.getString(cursor.getColumnIndex(COLUMN_ACTIVIDADES)),
                    presupuesto = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRESUPUESTO))
                )
                viajesList.add(viaje)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return viajesList
    }

    // Método para obtener un viaje por ID
    fun getViaje(id: Int): Viaje? {
        val db = this.readableDatabase
        val cursor: Cursor = db.query(
            TABLE_VIAJES, null, "$COLUMN_ID=?", arrayOf(id.toString()), null, null, null
        )

        if (cursor.moveToFirst()) {
            val viaje = Viaje(
                id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                destino = cursor.getString(cursor.getColumnIndex(COLUMN_DESTINO)),
                fechaInicio = cursor.getString(cursor.getColumnIndex(COLUMN_FECHA_INICIO)),
                fechaFin = cursor.getString(cursor.getColumnIndex(COLUMN_FECHA_FIN)),
                actividades = cursor.getString(cursor.getColumnIndex(COLUMN_ACTIVIDADES)),
                presupuesto = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRESUPUESTO))
            )
            cursor.close()
            return viaje
        }
        cursor.close()
        return null
    }

    // Método para actualizar un viaje
    fun updateViaje(viaje: Viaje) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_DESTINO, viaje.destino)
            put(COLUMN_FECHA_INICIO, viaje.fechaInicio)
            put(COLUMN_FECHA_FIN, viaje.fechaFin)
            put(COLUMN_ACTIVIDADES, viaje.actividades)
            put(COLUMN_PRESUPUESTO, viaje.presupuesto)
        }
        db.update(TABLE_VIAJES, values, "$COLUMN_ID=?", arrayOf(viaje.id.toString()))
        db.close()
    }

    // Método para eliminar un viaje
    fun deleteViaje(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_VIAJES, "$COLUMN_ID=?", arrayOf(id.toString()))
        db.close()
    }
}

