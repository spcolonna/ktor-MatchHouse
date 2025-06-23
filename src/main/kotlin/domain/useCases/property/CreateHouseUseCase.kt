package domain.useCases.property

import delivery.dtos.CreateHouseDto
import domain.builders.PropertyBuilder
import infra.interfaces.ICreateHouseRepository

class CreateHouseUseCase(private val repository: ICreateHouseRepository, private val builder: PropertyBuilder) {

    fun execute(dto: CreateHouseDto) = repository.store(builder.fromDto(dto))
}

