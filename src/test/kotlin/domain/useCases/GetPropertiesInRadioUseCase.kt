package domain.useCases

import domain.entities.Point
import domain.entities.Property

class GetPropertiesInRadioUseCase(
    private val calculateDistance: CalculateDistanceUseCase,
    private val propertiesRepo: IPropertiesRepository
) {

    fun execute(userLocation: Point, expectedDistance: Int): List<Property> {
        return propertiesRepo.getAllProperties()
            .filter { calculateDistance.execute(userLocation, it.point) <= expectedDistance }
    }

}
