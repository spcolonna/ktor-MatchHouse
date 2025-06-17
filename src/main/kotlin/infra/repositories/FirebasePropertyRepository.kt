package com.example.infra.repositories

import com.google.cloud.firestore.Firestore
import com.google.firebase.cloud.FirestoreClient
import domain.entities.Property
import infra.interfaces.ICreatePropertyRepository

class FirebasePropertyRepository : ICreatePropertyRepository {

    private val db: Firestore = FirestoreClient.getFirestore()

    override fun store(property: Property): String {
        val housesCollection = db.collection("houses")
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
}