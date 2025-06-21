package com.example.infra.repositories

import com.google.cloud.Timestamp
import com.google.cloud.firestore.Firestore
import com.google.firebase.cloud.FirestoreClient
import domain.entities.Favourites
import infra.interfaces.IFavouriteRepository

class FirebaseFavouriteRepository : IFavouriteRepository {

    private val db: Firestore = FirestoreClient.getFirestore()
    private val usersCollectionName = "users"
    private val favoritesSubCollectionName = "favorites"


    override fun store(userId: String, houseId: String) {
        val favoriteDocument = db.collection(usersCollectionName).document(userId)
            .collection(favoritesSubCollectionName).document(houseId)

        val favoriteData = mapOf("addedAt" to Timestamp.now())
        favoriteDocument.set(favoriteData).get()

        println("Casa $houseId añadida a favoritos para el usuario $userId")
    }

    override fun get(idUser: String): Favourites {
        val favoritesCollection = db.collection(usersCollectionName).document(idUser)
            .collection(favoritesSubCollectionName)

        val querySnapshot = favoritesCollection.get().get()
        val houseIds = querySnapshot.documents.map { document ->
            document.id
        }

        return Favourites(idUser = idUser, idHouses = houseIds)
    }

    override fun hasFavouriteList(userId: String): Boolean {
        val favoritesCollection = db.collection(usersCollectionName).document(userId)
            .collection(favoritesSubCollectionName)
        val query = favoritesCollection.limit(1)
        val querySnapshot = query.get().get()
        return !querySnapshot.isEmpty
    }

    override fun remove(userId: String, houseId: String) {
        val favoriteDocument = db.collection(usersCollectionName).document(userId)
            .collection(favoritesSubCollectionName).document(houseId)

        favoriteDocument.delete().get()

        println("Casa $houseId eliminada de favoritos para el usuario $userId")
    }
}