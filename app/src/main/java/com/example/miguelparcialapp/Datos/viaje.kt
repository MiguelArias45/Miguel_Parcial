package com.example.miguelparcialapp.Datos

import android.os.Parcel
import android.os.Parcelable

data class Viaje(
    val id: Long,
    val destino: String,
    val fechaInicio: String,
    val fechaFin: String,
    val actividades: String,
    val presupuesto: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(destino)
        parcel.writeString(fechaInicio)
        parcel.writeString(fechaFin)
        parcel.writeString(actividades)
        parcel.writeDouble(presupuesto)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Viaje> {
        override fun createFromParcel(parcel: Parcel): Viaje {
            return Viaje(parcel)
        }

        override fun newArray(size: Int): Array<Viaje?> {
            return arrayOfNulls(size)
        }
    }
}


