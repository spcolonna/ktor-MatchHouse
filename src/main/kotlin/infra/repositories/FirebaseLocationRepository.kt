package com.example.infra.repositories

import com.example.infra.interfaces.ILocationRepository
import com.google.cloud.firestore.Firestore
import com.google.firebase.cloud.FirestoreClient

class FirebaseLocationRepository: ILocationRepository {
    private val db: Firestore = FirestoreClient.getFirestore()
    private val location = "locations"

    override fun getCountries(): List<String> {
        val countriesSnapshot = db.collection(location).get().get()
        return countriesSnapshot.documents.map { document -> document.id }
    }

    override fun getDepartment(countryName: String): List<String> {
        val departmentsSnapshot = db.collection(location)
            .document(countryName)
            .collection("departments")
            .get()
            .get()
        return departmentsSnapshot.documents.map { document -> document.id }
    }

    override fun getNeighborhood(countryName: String, departmentName: String): List<String> {
        val neighborhoodsSnapshot = db.collection(location)
            .document(countryName)
            .collection("departments")
            .document(departmentName)
            .collection("neighborhoods")
            .get()
            .get()
        return neighborhoodsSnapshot.documents.map { document -> document.id }
    }
}