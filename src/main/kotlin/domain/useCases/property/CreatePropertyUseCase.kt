package domain.useCases.property

import delivery.dtos.CreatePropertyDto
import domain.builders.PropertyBuilder
import infra.interfaces.ICreatePropertyRepository

class CreatePropertyUseCase(private val repository: ICreatePropertyRepository, private val builder: PropertyBuilder) {

    fun execute(dto: CreatePropertyDto) = repository.store(builder.fromDto(dto))
}

