package domain.entities

data class Property(
    var id: String,
    val point: Point,
    val title: String,
    val price: Int,
    val bedrooms: Int,
    val bathrooms: Int,
    val area: Double
) {

}