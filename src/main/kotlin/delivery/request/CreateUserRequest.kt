package delivery.request

import com.example.delivery.dtos.CreateUserDto
import com.example.delivery.enums.UserRole


@kotlinx.serialization.Serializable
class CreateUserRequest(
    val id: String,
    val name: String,
    val mail: String,
    val phoneNumber: String,
    val role: UserRole,
    val agencyName: String) {
    fun toDto() = CreateUserDto(id, name, mail, phoneNumber, role, agencyName)

}
