package com.example.infra.repositories

import com.example.infra.interfaces.IHouseRepository
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.GeoPoint
import com.google.firebase.cloud.FirestoreClient
import domain.entities.Point
import domain.entities.House
import infra.interfaces.ICreateHouseRepository

class FirebasePropertyRepository : ICreateHouseRepository, IHouseRepository {

    private val documentName = "houses"
    private val db: Firestore = FirestoreClient.getFirestore()

    private fun documentToProperty(document: com.google.cloud.firestore.DocumentSnapshot): House? {
        return try {
            val pointMap = document.get("point") as? Map<String, Any>
                ?: document.getGeoPoint("point")?.let { mapOf("lat" to it.latitude, "lon" to it.longitude) }
                ?: return null

            val lat = (pointMap["lat"] as? Number)?.toDouble() ?: 0.0
            val lon = (pointMap["lon"] as? Number)?.toDouble() ?: 0.0

            @Suppress("UNCHECKED_CAST")
            val imageUrls = document.get("imageUrls") as? List<String> ?: emptyList()

            House(
                id = document.getString("id") ?: document.id,
                ownerId = document.getString("ownerId") ?: "",
                title = document.getString("title") ?: "Propiedad sin título",
                point = Point(lat, lon),
                price = (document.get("price") as? Number ?: 0).toInt(),
                bedrooms = (document.get("bedrooms") as? Number ?: 0).toInt(),
                bathrooms = (document.get("bathrooms") as? Number ?: 0).toInt(),
                area = (document.get("area") as? Number ?: 0.0).toDouble(),
                imageUrls = imageUrls
            )
        } catch (e: Exception) {
            println("Error de formato al parsear el documento ${document.id}: ${e.message}")
            null
        }
    }

    override fun store(property: House): String {
        val housesCollection = db.collection(documentName)
        val houseId = property.id.ifEmpty { housesCollection.document().id }
        val houseDocument = housesCollection.document(houseId)
        property.id = houseId

        val houseData = mapOf(
            "id" to houseId,
            "ownerId" to property.ownerId,
            "title" to property.title,
            "point" to GeoPoint(property.point.lat, property.point.lon),
            "price" to property.price,
            "bedrooms" to property.bedrooms,
            "bathrooms" to property.bathrooms,
            "area" to property.area,
            "imageUrls" to property.imageUrls,
            "createdAt" to com.google.cloud.Timestamp.now()
        )

        houseDocument.set(houseData).get()
        println("Casa guardada en Firestore con ID: $houseId")
        return houseId
    }

    override fun getHouses(): List<House> {
        val housesCollection = db.collection(documentName)
        val querySnapshot = housesCollection.get().get()
        return querySnapshot.documents.mapNotNull { documentToProperty(it) }
    }

    override fun houseExist(houseId: String): Boolean {
        val documentReference = db.collection(documentName).document(houseId)
        val documentSnapshot = documentReference.get().get()
        return documentSnapshot.exists()
    }

    override fun getHouseById(houseId: String): House {
        val documentReference = db.collection(documentName).document(houseId)
        val documentSnapshot = documentReference.get().get()

        if (documentSnapshot.exists()) {
            val property = documentToProperty(documentSnapshot)
            if (property != null) {
                return property
            }
        }
        throw NoSuchElementException("No se encontró una casa con el ID: $houseId o el formato de datos es incorrecto.")
    }
}