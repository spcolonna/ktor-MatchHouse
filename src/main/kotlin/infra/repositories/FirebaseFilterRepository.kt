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
}