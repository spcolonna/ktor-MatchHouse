package domain.entities

import com.example.delivery.enums.UserRole

data class User(
    val id: String,
    val name: String,
    val mail: String,
    val phoneNumber: String,
    val role: UserRole,
    val agencyName: String){
    companion object {
        fun fromMap(data: Map<String, Any>?, documentId: String): User {
            val roleString = data?.get("role") as? String
            val role = when (roleString) {
                "AGENCY" -> UserRole.AGENCY
                else -> UserRole.PERSON
            }

            return User(
                id = documentId,
                mail = data?.get("mail") as? String ?: "",
                name = data?.get("name") as? String ?: "",
                phoneNumber = data?.get("phoneNumber") as? String ?: "",
                role = role,
                agencyName = data?.get("agencyName") as? String ?: ""
            )
        }
    }
}
