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
        fun fromMap(data: Map<String, Any>, documentId: String): User {
            val role = when (data["role"] as? String) {
                "agency" -> UserRole.AGENCY
                else -> UserRole.PERSON
            }

            return User(
                id = documentId,
                mail = data["mail"] as? String ?: "",
                name = data["name"] as? String ?: "",
                phoneNumber = data["phoneNumber"] as? String ?: "",
                role = role,
                agencyName = data["agencyName"] as? String ?: ""
            )
        }
    }
}
