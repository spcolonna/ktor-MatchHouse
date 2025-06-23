package delivery.dtos

import com.example.delivery.request.CreateHouseRequest

class CreateHouseDto(
    val ownerId: String,
    val lon: Double,
    val lat: Double,
    val title: String,
    val price: Int,
    val bedrooms: Int,
    val bathrooms: Int,
    val area: Double,
    val imageUrls: List<String>
) {
    companion object {
        fun fromRequest(request: CreateHouseRequest): CreateHouseDto {
            return CreateHouseDto(
                request.ownerId,
                request.lon,
                request.lat,
                request.title,
                request.price,
                request.bedrooms,
                request.bathrooms,
                request.area,
                listOf()
            )
        }
    }

}