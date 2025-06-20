package com.example.infra.repositories

import com.google.cloud.firestore.Firestore
import com.google.firebase.cloud.FirestoreClient
import domain.entities.User
import infra.interfaces.IUserRepository

class FirebaseUserRepository : IUserRepository {

    private val documentName = "users"
    private val db: Firestore = FirestoreClient.getFirestore()

    override fun store(user: User) {
        val usersCollection = db.collection(documentName)
        val newUserDocument = usersCollection.document(user.id)

        val userData = mapOf(
            "id" to user.id,
            "name" to user.name,
            "mail" to user.mail,
            "phoneNumber" to user.phoneNumber,
            "role" to user.role,
            "agencyName" to user.agencyName,
            "createdAt" to com.google.cloud.Timestamp.now()
        )

        newUserDocument.set(userData).get()
        println("Casa guardada en Firestore con ID: ${newUserDocument.id}")
    }

    override fun getUserByMail(mail: String): User? {
        val usersCollection = db.collection(documentName)
        val query = usersCollection.whereEqualTo("mail", mail).limit(1)
        val querySnapshot = query.get().get()

        if (querySnapshot.isEmpty) {
            return null
        }

        val document = querySnapshot.documents[0]
        return User.fromMap(document.data, document.id)
    }

    override fun userExists(userId: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteUser(userId: String) {
        TODO("Not yet implemented")
    }

    override fun getStoredUser(): User {
        TODO("Not yet implemented")
    }

    override fun update(user: User) {
        TODO("Not yet implemented")
    }

    override fun getUser(userId: String): User {
        TODO("Not yet implemented")
    }
}