package com.example.registroempleados.model

import java.util.UUID

/**
 * Modelo de datos que representa a un empleado registrado en la aplicación.
 *
 * @property id Identificador único generado automáticamente, usado como key
 *              en el LazyColumn para optimizar recomposiciones y permitir
 *              eliminar el ítem correcto.
 */
data class Empleado(
    val id: String = UUID.randomUUID().toString(),
    val nombreCompleto: String,
    val cargo: String,
    val departamento: String,
    val salario: String,
    val fechaContratacion: String
)
