package com.example.registroempleados.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.registroempleados.model.Empleado


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmpleadoScreen(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    // Lista de empleados en memoria. mutableStateListOf hace que Compose
    // recomponga automáticamente el LazyColumn al agregar/eliminar ítems.
    val empleados = remember { mutableStateListOf<Empleado>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registro de Empleados") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    IconButton(onClick = onToggleTheme) {
                        Icon(
                            imageVector = if (isDarkTheme) Icons.Filled.Brightness7 else Icons.Filled.Brightness4,
                            contentDescription = "Cambiar tema",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            FormularioEmpleado(
                onAgregar = { empleado -> empleados.add(0, empleado) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            ListaEmpleados(
                empleados = empleados,
                onEliminar = { empleado -> empleados.remove(empleado) }
            )
        }
    }
}

/**
 * Formulario de ingreso de datos de un nuevo empleado.
 */
@Composable
fun FormularioEmpleado(onAgregar: (Empleado) -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var cargo by remember { mutableStateOf("") }
    var departamento by remember { mutableStateOf("") }
    var salario by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Nuevo Empleado",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre completo") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = cargo,
                onValueChange = { cargo = it },
                label = { Text("Cargo") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = departamento,
                onValueChange = { departamento = it },
                label = { Text("Departamento") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = salario,
                onValueChange = { salario = it },
                label = { Text("Salario") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = fecha,
                onValueChange = { fecha = it },
                label = { Text("Fecha de contratación (dd/mm/aaaa)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            if (error) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Completá todos los campos antes de registrar.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelLarge
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    if (nombre.isBlank() || cargo.isBlank() || departamento.isBlank() ||
                        salario.isBlank() || fecha.isBlank()
                    ) {
                        error = true
                    } else {
                        error = false
                        onAgregar(
                            Empleado(
                                nombreCompleto = nombre,
                                cargo = cargo,
                                departamento = departamento,
                                salario = salario,
                                fechaContratacion = fecha
                            )
                        )
                        // Limpiar el formulario luego de registrar
                        nombre = ""; cargo = ""; departamento = ""; salario = ""; fecha = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar Empleado")
            }
        }
    }
}

/**
 * Lista vertical de empleados registrados, implementada con LazyColumn.
 */
@Composable
fun ListaEmpleados(
    empleados: List<Empleado>,
    onEliminar: (Empleado) -> Unit
) {
    if (empleados.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Todavía no hay empleados registrados.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(
                items = empleados,
                key = { it.id }
            ) { empleado ->
                EmpleadoItem(
                    empleado = empleado,
                    onEliminar = { onEliminar(empleado) }
                )
            }
        }
    }
}

/**
 * Ítem individual de la lista: nombre destacado arriba, una LazyRow con
 * el resto de los datos del empleado, y un botón de eliminar abajo.
 */
@Composable
fun EmpleadoItem(
    empleado: Empleado,
    onEliminar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Nombre completo destacado en la parte superior
            Text(
                text = empleado.nombreCompleto,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // LazyRow con el resto de los datos del empleado
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    DatoChip(etiqueta = "Cargo", valor = empleado.cargo)
                }
                item {
                    DatoChip(etiqueta = "Departamento", valor = empleado.departamento)
                }
                item {
                    DatoChip(etiqueta = "Salario", valor = empleado.salario)
                }
                item {
                    DatoChip(etiqueta = "Contratación", valor = empleado.fechaContratacion)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón de eliminación en la parte inferior del ítem
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = onEliminar,
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    )
                ) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
                    Spacer(modifier = Modifier.height(0.dp))
                    Text(text = "  Eliminar")
                }
            }
        }
    }
}

/** Pequeño "chip" reutilizable para mostrar un dato con su etiqueta dentro de la LazyRow. */
@Composable
private fun DatoChip(etiqueta: String, valor: String) {
    AssistChip(
        onClick = { /* solo informativo */ },
        label = {
            Column {
                Text(text = etiqueta, style = MaterialTheme.typography.labelLarge)
                Text(text = valor, style = MaterialTheme.typography.bodyLarge)
            }
        }
    )
}
