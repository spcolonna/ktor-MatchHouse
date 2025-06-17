package delivery.dtos

import com.example.delivery.request.CreateHouseRequest

class CreatePropertyDto(
    val lon: Double,
    val lat: Double,
    val title: String,
    val price: Int,
    val bedrooms: Int,
    val bathrooms: Int,
    val area: Double
) {
    companion object {
        fun fromRequest(request: CreateHouseRequest): CreatePropertyDto {
            return CreatePropertyDto(
                request.lon,
                request.lat,
                request.title,
                request.price,
                request.bedrooms,
                request.bathrooms,
                request.area
            )
        }
    }

}