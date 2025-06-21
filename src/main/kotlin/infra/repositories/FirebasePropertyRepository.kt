package com.example.infra.repositories

import com.example.infra.interfaces.IHouseRepository
import com.google.cloud.firestore.Firestore
import com.google.firebase.cloud.FirestoreClient
import domain.entities.Point
import domain.entities.Property
import infra.interfaces.ICreatePropertyRepository

class FirebasePropertyRepository : ICreatePropertyRepository, IHouseRepository {

    private val documentName = "houses"
    private val db: Firestore = FirestoreClient.getFirestore()

    override fun store(property: Property): String {
        val housesCollection = db.collection(documentName)
        val newHouseDocument = housesCollection.document()

        val houseData = mapOf(
            "id" to newHouseDocument.id,
            "title" to property.title,
            "point" to property.point,
            "price" to property.price,
            "bedrooms" to property.bedrooms,
            "bathrooms" to property.bathrooms,
            "area" to property.area,
            "createdAt" to com.google.cloud.Timestamp.now()
        )
        property.id = newHouseDocument.id

        newHouseDocument.set(houseData).get()
        println("Casa guardada en Firestore con ID: ${newHouseDocument.id}")

        return property.id
    }

    override fun getHouses(): List<Property> {
        val housesCollection = db.collection(documentName)
        val querySnapshot = housesCollection.get().get()

        return querySnapshot.documents.mapNotNull { document ->
            documentToProperty(document)
        }
    }

    override fun houseExist(houseId: String): Boolean {
        val documentReference = db.collection(documentName).document(houseId)
        val documentSnapshot = documentReference.get().get()

        return documentSnapshot.exists()
    }

    override fun getHouseById(houseId: String): Property {
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

    private fun documentToProperty(document: com.google.cloud.firestore.DocumentSnapshot): Property? {
        return try {
            val pointMap = document.get("point") as? Map<String, Any>
                ?: return null // Si no hay 'point', el documento es inválido

            val lat = (pointMap["lat"] as? Number)?.toDouble() ?: 0.0
            val lon = (pointMap["lon"] as? Number)?.toDouble() ?: 0.0

            val priceNum = document.get("price") as? Number ?: 0
            val bedroomsNum = document.get("bedrooms") as? Number ?: 0
            val bathroomsNum = document.get("bathrooms") as? Number ?: 0
            val areaNum = document.get("area") as? Number ?: 0.0

            Property(
                id = document.getString("id") ?: document.id,
                title = document.getString("title") ?: "Propiedad sin título",
                point = Point(lat, lon),
                price = priceNum.toInt(),
                bedrooms = bedroomsNum.toInt(),
                bathrooms = bathroomsNum.toInt(),
                area = areaNum.toDouble()
            )
        } catch (e: Exception) {
            println("Error de formato al parsear el documento ${document.id}: ${e.message}")
            null
        }
    }
}