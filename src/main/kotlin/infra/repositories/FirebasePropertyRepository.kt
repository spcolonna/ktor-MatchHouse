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
            try {
                val pointMap = document.get("point") as? Map<String, Any>
                    ?: return@mapNotNull null
                val lat = (pointMap["lat"] as? Number)?.toDouble() ?: 0.0
                val lon = (pointMap["lon"] as? Number)?.toDouble() ?: 0.0
                Property(
                    id = document.getString("id")!!,
                    title = document.getString("title")!!,
                    point = Point(lat,lon),
                    price = document.getLong("price")!!.toInt(),
                    bedrooms = document.getLong("bedrooms")!!.toInt(),
                    bathrooms = document.getLong("bathrooms")!!.toInt(),
                    area = document.getDouble("area")!!
                )
            } catch (e: Exception) {
                println("Error al parsear el documento ${document.id}: ${e.message}")
                null
            }
        }
    }

    override fun houseExist(houseId: String): Boolean {
        val documentReference = db.collection(documentName).document(houseId)
        val documentSnapshot = documentReference.get().get()

        return documentSnapshot.exists()
    }
}