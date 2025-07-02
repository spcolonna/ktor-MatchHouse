package com.example.infra.repositories

import com.example.domain.entities.UserFilter
import com.example.infra.interfaces.IFilterRepository
import com.google.cloud.firestore.Firestore
import com.google.firebase.cloud.FirestoreClient

class FirebaseFilterRepository : IFilterRepository {

    private val usersCollectionName = "users"
    private val db: Firestore = FirestoreClient.getFirestore()

    override fun store(userFilter: UserFilter) {
        val userDocument = db.collection(usersCollectionName).document(userFilter.userId)

        val filtersMap = userFilter.toMap()

        val dataToUpdate = mapOf("searchFilters" to filtersMap)
        userDocument.update(dataToUpdate).get()

        println("Filtros guardados para el usuario con ID: ${userFilter.userId}")
    }

    override fun update(userFilter: UserFilter) {
        val userDocument = db.collection(usersCollectionName).document(userFilter.userId)
        val filtersMap = userFilter.toMap()
        val dataToUpdate = mapOf("searchFilters" to filtersMap)

        userDocument.update(dataToUpdate).get()

        println("Filtros actualizados para el usuario con ID: ${userFilter.userId}")
    }

    override fun get(userId: String): UserFilter {
        val documentReference = db.collection(usersCollectionName).document(userId)
        val documentSnapshot = documentReference.get().get()

        if (documentSnapshot.exists()) {
            val filtersMap = documentSnapshot.get("searchFilters") as? Map<String, Any>

            if (filtersMap != null) {
                println("Filtros encontrados para el usuario $userId.")
                return UserFilter.fromMap(userId, filtersMap)
            }
        }

        println("No se encontraron filtros para el usuario $userId. Devolviendo valores por defecto.")
        return UserFilter(
            userId = userId,
            country = "Uruguay",
            department = "",
            neighborhood = "",
            minPrice = 0.0,
            maxPrice = 500000.0,
            bedrooms = 0,
            bathrooms = 0,
            area = 0
        )
    }

    override fun exist(userId: String): Boolean {
        val documentReference = db.collection(usersCollectionName).document(userId)
        val documentSnapshot = documentReference.get().get()

        return documentSnapshot.exists() && documentSnapshot.contains("searchFilters")
    }
}