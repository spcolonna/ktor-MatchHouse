package delivery.providers

import com.example.infra.services.IdGenerator
import domain.interfaces.IIdGenerator

object ServiceProvider {
    fun getIdGenerator(): IIdGenerator {
        return IdGenerator()
    }

}
