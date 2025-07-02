package domain.entities
import kotlinx.serialization.Serializable

@Serializable
data class House(
    var id: String,
    var ownerId: String,
    val point: Point,
    val title: String,
    val price: Int,
    val bedrooms: Int,
    val bathrooms: Int,
    val area: Double,
    val imageUrls: List<String>,
    var geohash: String? = null
)