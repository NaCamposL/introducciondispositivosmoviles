package com.example.eventos.data

import java.util.UUID

/**
 * Representa un evento registrado en la aplicación.
 */
data class Evento(
    val id: String = UUID.randomUUID().toString(),
    val nombre: String,
    val fecha: String,
    val ubicacion: String,
    val categoria: String,
    val precio: String
)
