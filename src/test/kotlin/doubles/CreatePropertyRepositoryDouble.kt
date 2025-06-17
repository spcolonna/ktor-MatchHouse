package doubles

import infra.interfaces.ICreatePropertyRepository
import domain.entities.Property

class CreatePropertyRepositoryDouble : ICreatePropertyRepository {
    lateinit var storeProperty: Property
    override fun store(property: Property): String {
        storeProperty = property
        return property.id
    }
}