package com.example.seeders.DeleteHouseDB

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import com.google.firebase.cloud.StorageClient
import java.io.InputStream

/**
 * ¡¡¡ADVERTENCIA: SCRIPT DESTRUCTIVO!!!
 * Este script está diseñado para borrar TODAS las casas, TODAS sus referencias
 * en favoritos y descubrimiento, Y TODAS LAS FOTOS ASOCIADAS en Firebase Storage.
 *
 * ÚSALO CON MÁXIMA PRECAUCIÓN.
 *
 * CÓMO USARLO:
 * 1. Coloca este archivo en tu proyecto Ktor.
 * 2. Asegúrate de que tu 'serviceAccountKey.json' esté en 'src/main/resources'.
 * 3. Haz clic derecho en este archivo en tu IDE y selecciona "Run".
 */
suspend fun main() {
    // --- Inicialización de Firebase (necesaria para que el script se conecte) ---
    val serviceAccount: InputStream? = Thread.currentThread().contextClassLoader.getResourceAsStream("serviceAccountKey.json")
    if (serviceAccount == null) {
        println("ERROR: No se encontró el archivo serviceAccountKey.json.")
        return
    }
    // NUEVO: Añadimos la URL del bucket de Storage a las opciones
    val options: FirebaseOptions = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .setStorageBucket("matchhouse-ca5a8.appspot.com") // <-- IMPORTANTE: Reemplaza con el ID de tu bucket
        .build()

    if (FirebaseApp.getApps().isEmpty()) {
        FirebaseApp.initializeApp(options)
        println("Firebase Admin SDK inicializado para el script de limpieza.")
    }
    // --------------------------------------------------------------------------

    val db = FirestoreClient.getFirestore()
    val storage = StorageClient.getInstance().bucket() // NUEVO: Obtenemos la instancia de Storage
    val firestoreBatch = db.batch()
    var firestoreDeleteCounter = 0

    println("Iniciando proceso de limpieza de casas y sus dependencias...")

    // 1. Obtener todos los IDs de usuarios y casas
    println("Paso 1: Obteniendo IDs de todos los usuarios y casas...")
    val userIds = db.collection("users").get().get().documents.map { it.id }
    val houseIds = db.collection("houses").get().get().documents.map { it.id }
    println("Se encontraron ${userIds.size} usuarios y ${houseIds.size} casas.")

    // 2. Borrar referencias en favoritos (Firestore)
    println("Paso 2: Preparando borrado de referencias de favoritos...")
    for (userId in userIds) {
        for (houseId in houseIds) {
            val favoriteRef = db.collection("users").document(userId).collection("favorites").document(houseId)
            firestoreBatch.delete(favoriteRef)
            firestoreDeleteCounter++
        }
    }

    // 3. Borrar referencias en la cola de descubrimiento (Firestore)
    println("Paso 3: Preparando borrado de referencias de la cola de descubrimiento...")
    for (userId in userIds) {
        for (houseId in houseIds) {
            val discoveryRef = db.collection("users").document(userId).collection("discoveryQueue").document(houseId)
            firestoreBatch.delete(discoveryRef)
            firestoreDeleteCounter++
        }
    }

    // 4. Borrar documentos de casas (Firestore)
    println("Paso 4: Preparando borrado de documentos principales de las casas...")
    for (houseId in houseIds) {
        val houseRef = db.collection("houses").document(houseId)
        firestoreBatch.delete(houseRef)
        firestoreDeleteCounter++
    }

    // 5. Ejecutar el lote de borrado de Firestore
    if (firestoreDeleteCounter > 0) {
        println("Ejecutando lote de borrado para $firestoreDeleteCounter operaciones en Firestore...")
        firestoreBatch.commit().get()
        println("¡Limpieza de Firestore completada!")
    } else {
        println("No se encontraron documentos para eliminar en Firestore.")
    }

    // --- NUEVO: PASO 6 - BORRAR ARCHIVOS DE STORAGE ---
    println("Paso 6: Eliminando archivos de imágenes de Firebase Storage...")
    var storageDeleteCounter = 0
    for (houseId in houseIds) {
        // Obtenemos todos los blobs (archivos) dentro de la carpeta de la casa
        val blobs = storage.list(
            com.google.cloud.storage.Storage.BlobListOption.prefix("houses/$houseId/")
        ).values

        for (blob in blobs) {
            blob.delete()
            println(" -> Archivo borrado: ${blob.name}")
            storageDeleteCounter++
        }
    }

    if (storageDeleteCounter > 0) {
        println("¡Limpieza de Storage completada! Se eliminaron $storageDeleteCounter archivos.")
    } else {
        println("No se encontraron archivos para eliminar en Storage.")
    }

    println("--- PROCESO DE LIMPIEZA FINALIZADO ---")
}
