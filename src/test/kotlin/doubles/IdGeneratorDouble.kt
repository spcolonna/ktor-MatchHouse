package doubles

import domain.interfaces.IIdGenerator

class IdGeneratorDouble(private val id: String) : IIdGenerator {

    override fun execute() = id
}
