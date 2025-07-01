package com.example.seeders

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import java.io.InputStream

/**
 * Este es un script de ejecución única para poblar tu base de datos de Firestore
 * con los departamentos y ciudades/barrios de Uruguay.
 *
 * CÓMO USARLO:
 * 1. Coloca este archivo en una nueva carpeta dentro de tu proyecto Ktor,
 * por ejemplo: src/main/kotlin/com/example/seeders/LocationSeeder.kt
 * 2. Asegúrate de que tu archivo 'serviceAccountKey.json' esté en 'src/main/resources'.
 * 3. Haz clic derecho en este archivo en tu IDE (IntelliJ/WebStorm) y selecciona "Run 'LocationSeederKt'".
 * 4. El script se ejecutará y cargará todos los datos a tu Firestore.
 * 5. Una vez que termine, puedes borrar o comentar el contenido de la función main()
 * para no volver a ejecutarlo por accidente.
 */
fun main() {
    // --- Inicialización de Firebase (necesaria para que el script se conecte) ---
    val serviceAccount: InputStream? = Thread.currentThread().contextClassLoader.getResourceAsStream("serviceAccountKey.json")
    if (serviceAccount == null) {
        println("ERROR: No se encontró el archivo serviceAccountKey.json.")
        return
    }
    val options: FirebaseOptions = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .build()
    if (FirebaseApp.getApps().isEmpty()) {
        FirebaseApp.initializeApp(options)
        println("Firebase Admin SDK inicializado para el seeder.")
    }
    // --------------------------------------------------------------------------

    val db = FirestoreClient.getFirestore()

    // --- DATOS DE URUGUAY ---
    // Fuente: Datos demográficos y geográficos generales. Puede no ser exhaustiva.
    val uruguayData = mapOf(
        "Artigas" to listOf("Artigas", "Bella Unión"),
        "Canelones" to listOf("Canelones", "Ciudad de la Costa", "Las Piedras", "Pando", "La Paz", "Atlántida"),
        "Cerro Largo" to listOf("Melo", "Río Branco"),
        "Colonia" to listOf("Colonia del Sacramento", "Carmelo", "Juan Lacaze", "Nueva Helvecia"),
        "Durazno" to listOf("Durazno", "Sarandí del Yí"),
        "Flores" to listOf("Trinidad", "Ismael Cortinas"),
        "Florida" to listOf("Florida", "Sarandí Grande", "Casupá"),
        "Lavalleja" to listOf("Minas", "José Pedro Varela", "Solís de Mataojo"),
        "Maldonado" to listOf("Maldonado", "Punta del Este", "San Carlos", "Piriápolis", "Pan de Azúcar"),
        "Montevideo" to listOf(
            "Aguada", "Barrio Sur", "Carrasco", "Centro", "Ciudad Vieja", "Cordón", "Malvín",
            "Palermo", "Parque Batlle", "Parque Rodó", "Pocitos", "Punta Carretas", "Punta Gorda", "Tres Cruces"
        ),
        "Paysandú" to listOf("Paysandú", "Guichón"),
        "Río Negro" to listOf("Fray Bentos", "Young"),
        "Rivera" to listOf("Rivera", "Tranqueras"),
        "Rocha" to listOf("Rocha", "Chuy", "La Paloma", "Castillos"),
        "Salto" to listOf("Salto", "Daymán", "Arapey"),
        "San José" to listOf("San José de Mayo", "Ciudad del Plata", "Libertad"),
        "Soriano" to listOf("Mercedes", "Dolores", "Cardona"),
        "Tacuarembó" to listOf("Tacuarembó", "Paso de los Toros", "San Gregorio de Polanco"),
        "Treinta y Tres" to listOf("Treinta y Tres", "Vergara")
    )

    println("Iniciando carga de datos de ubicación para Uruguay...")

    // Usamos un WriteBatch para agrupar todas las operaciones en una sola llamada
    val batch = db.batch()

    // Documento principal para el país
    val countryDocRef = db.collection("locations").document("Uruguay")
    batch.set(countryDocRef, mapOf("name" to "Uruguay")) // Creamos el documento del país

    // Iteramos sobre cada departamento y sus barrios/ciudades
    uruguayData.forEach { (departmentName, neighborhoods) ->
        val departmentDocRef = countryDocRef.collection("departments").document(departmentName)
        batch.set(departmentDocRef, mapOf("name" to departmentName)) // Creamos el documento del departamento

        // Añadimos cada barrio/ciudad a la subcolección 'neighborhoods'
        neighborhoods.forEach { neighborhoodName ->
            val neighborhoodDocRef = departmentDocRef.collection("neighborhoods").document(neighborhoodName)
            batch.set(neighborhoodDocRef, mapOf("name" to neighborhoodName)) // Creamos el documento del barrio
        }
        println("Preparando $departmentName con ${neighborhoods.size} barrios/ciudades...")
    }

    // Ejecutamos todas las operaciones de escritura en la base de datos
    batch.commit().get()

    println("¡Carga completada! Todos los datos de Uruguay han sido guardados en Firestore.")
}

