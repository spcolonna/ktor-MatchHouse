package domain.builders

import delivery.dtos.CreateHouseDto
import domain.entities.Point
import domain.entities.House
import domain.interfaces.IIdGenerator

class PropertyBuilder(private val idGenerator: IIdGenerator) {
    fun fromDto(dto: CreateHouseDto) = House(
        idGenerator.execute(),
        dto.ownerId,
        Point(dto.lat, dto.lon),
        dto.title,
        dto.price,
        dto.bedrooms,
        dto.bathrooms,
        dto.area,
        dto.imageUrls
    )

}