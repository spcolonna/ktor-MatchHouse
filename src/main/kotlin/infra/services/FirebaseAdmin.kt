package com.example.infra.services

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import java.io.InputStream

class FirebaseAdmin {

    fun initializeFirebase() {
        // Obtenemos el InputStream del archivo de clave que pusimos en resources
        val serviceAccount: InputStream? =
            Thread.currentThread().contextClassLoader.getResourceAsStream("serviceAccountKey.json")

        if (serviceAccount == null) {
            println("ERROR: No se encontró el archivo serviceAccountKey.json. Asegúrate de que esté en src/main/resources.")
            return
        }

        // Configuramos las opciones de Firebase con nuestras credenciales
        val options: FirebaseOptions = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build()

        // Inicializamos la app de Firebase solo si no ha sido inicializada antes
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options)
            println("Firebase Admin SDK inicializado correctamente.")
        }
    }

}