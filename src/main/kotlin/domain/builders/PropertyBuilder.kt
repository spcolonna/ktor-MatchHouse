package domain.builders

import delivery.dtos.CreatePropertyDto
import domain.entities.Point
import domain.entities.Property
import domain.interfaces.IIdGenerator

class PropertyBuilder(private val idGenerator: IIdGenerator) {
    fun fromDto(dto: CreatePropertyDto) = Property(
        idGenerator.execute(),
        Point(dto.lon, dto.lat),
        dto.title,
        dto.price,
        dto.bedrooms,
        dto.bathrooms,
        dto.area
    )

}