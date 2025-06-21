package com.example.domain.useCases.property

import com.example.infra.interfaces.IHouseRepository

class GetHouseByIdUseCase(private val repository: IHouseRepository) {
    fun execute(houseId: String) = repository.getHouseById(houseId)
    fun validate(houseId: String) = repository.houseExist(houseId)

}
