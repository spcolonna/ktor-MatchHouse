package com.example.infra.repositories

import com.example.infra.interfaces.IHouseRepository
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.GeoPoint
import com.google.firebase.cloud.FirestoreClient
import domain.entities.Point
import domain.entities.House
import infra.interfaces.ICreateHouseRepository
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation

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
                imageUrls = imageUrls,
                country = document.getString("country") ?: "",
                department = document.getString("department") ?: "",
                neighborhood = document.getString("neighborhood") ?: ""
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

        val geoHash = GeoFireUtils.getGeoHashForLocation(
            GeoLocation(property.point.lat, property.point.lon)
        )
        property.geohash = geoHash

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
            "geohash" to geoHash,
            "country" to property.country,
            "department" to property.department,
            "neighborhood" to property.neighborhood,
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

    override fun getUserHouses(userId: String): List<House> {
        val housesCollection = db.collection(documentName)
        val query = housesCollection.whereEqualTo("ownerId", userId)
        val querySnapshot = query.get().get()
        return querySnapshot.documents.mapNotNull { documentToProperty(it) }
    }

    override fun findNearbyHouses(lat: Double, lon: Double, radiusInM: Double): List<House> {
        val center = GeoLocation(lat, lon)

        val bounds = GeoFireUtils.getGeoHashQueryBounds(center, radiusInM)
        val tasks = mutableListOf<com.google.api.core.ApiFuture<com.google.cloud.firestore.QuerySnapshot>>()

        for (b in bounds) {
            val query = db.collection(documentName)
                .orderBy("geohash")
                .startAt(b.startHash)
                .endAt(b.endHash)
            tasks.add(query.get())
        }

        val matchingDocs = mutableListOf<House>()

        for (task in tasks) {
            val snap = task.get()
            for (doc in snap.documents) {
                val docLat = doc.getGeoPoint("point")?.latitude ?: continue
                val docLon = doc.getGeoPoint("point")?.longitude ?: continue
                val docLocation = GeoLocation(docLat, docLon)

                val distanceInM = GeoFireUtils.getDistanceBetween(docLocation, center)
                if (distanceInM <= radiusInM) {
                    documentToProperty(doc)?.let { matchingDocs.add(it) }
                }
            }
        }

        return matchingDocs
    }
}