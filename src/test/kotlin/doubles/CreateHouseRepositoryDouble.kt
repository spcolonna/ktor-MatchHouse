package doubles

import infra.interfaces.ICreateHouseRepository
import domain.entities.House

class CreateHouseRepositoryDouble : ICreateHouseRepository {
    lateinit var storeProperty: House
    override fun store(property: House): String {
        storeProperty = property
        return property.id
    }
}