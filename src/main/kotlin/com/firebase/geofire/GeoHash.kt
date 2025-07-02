package com.firebase.geofire

object GeoHash {
    private const val BASE32 = "0123456789bcdefghjkmnpqrstuvwxyz"

    private val BORDERS = mapOf(
        Direction.TOP to mapOf(
            "even" to "bcfguvyz",
            "odd" to "prxz"
        ),
        Direction.BOTTOM to mapOf(
            "even" to "0145hjnp",
            "odd" to "028b"
        ),
        Direction.RIGHT to mapOf(
            "even" to "prxz",
            "odd" to "bcfguvyz"
        ),
        Direction.LEFT to mapOf(
            "even" to "028b",
            "odd" to "0145hjnp"
        )
    )

    private val NEIGHBORS = mapOf(
        Direction.TOP to mapOf(
            "even" to "p0r21436x8zb9dcf5h7kjnmqesgutwvy",
            "odd" to "bc01fg45238967deuvhjyznpkmstqrwx"
        ),
        Direction.BOTTOM to mapOf(
            "even" to "14365h7k9dcfesgujnmqp0r2twvyx8zb",
            "odd" to "238967debc01fg45kmstqrwxuvhjyznp"
        ),
        Direction.RIGHT to mapOf(
            "even" to "bc01fg45238967deuvhjyznpkmstqrwx",
            "odd" to "p0r21436x8zb9dcf5h7kjnmqesgutwvy"
        ),
        Direction.LEFT to mapOf(
            "even" to "238967debc01fg45kmstqrwxuvhjyznp",
            "odd" to "14365h7k9dcfesgujnmqp0r2twvyx8zb"
        )
    )

    fun encode(latitude: Double, longitude: Double, precision: Int = 12): String {
        val latInterval = doubleArrayOf(-90.0, 90.0)
        val lonInterval = doubleArrayOf(-180.0, 180.0)

        val geohash = StringBuilder()
        var isEven = true
        var bit = 0
        var ch = 0

        while (geohash.length < precision) {
            val mid: Double
            if (isEven) {
                mid = (lonInterval[0] + lonInterval[1]) / 2
                if (longitude > mid) {
                    ch = ch or (1 shl (4 - bit))
                    lonInterval[0] = mid
                } else {
                    lonInterval[1] = mid
                }
            } else {
                mid = (latInterval[0] + latInterval[1]) / 2
                if (latitude > mid) {
                    ch = ch or (1 shl (4 - bit))
                    latInterval[0] = mid
                } else {
                    latInterval[1] = mid
                }
            }

            isEven = !isEven
            if (bit < 4) {
                bit++
            } else {
                geohash.append(BASE32[ch])
                bit = 0
                ch = 0
            }
        }
        return geohash.toString()
    }

    fun neighbors(hash: String): List<String> {
        return listOf(
            calculateAdjacent(hash, Direction.TOP),
            calculateAdjacent(hash, Direction.BOTTOM),
            calculateAdjacent(hash, Direction.RIGHT),
            calculateAdjacent(hash, Direction.LEFT),
            calculateAdjacent(calculateAdjacent(hash, Direction.TOP), Direction.RIGHT),
            calculateAdjacent(calculateAdjacent(hash, Direction.TOP), Direction.LEFT),
            calculateAdjacent(calculateAdjacent(hash, Direction.BOTTOM), Direction.RIGHT),
            calculateAdjacent(calculateAdjacent(hash, Direction.BOTTOM), Direction.LEFT)
        )
    }

    fun calculateAdjacent(hash: String, direction: Direction): String {
        val hashLower = hash.lowercase()
        val lastChar = hashLower.last()
        val type = if (hashLower.length % 2 == 0) "even" else "odd"

        val base = hashLower.substring(0, hashLower.length - 1)
        val border = BORDERS[direction]?.get(type) ?: ""
        val neighbor = NEIGHBORS[direction]?.get(type) ?: ""

        var adjBase = base
        if (border.contains(lastChar) && adjBase.isNotEmpty()) {
            adjBase = calculateAdjacent(adjBase, direction)
        }

        val neighborIndex = neighbor.indexOf(lastChar)
        val newChar = if (neighborIndex >= 0) BASE32[neighborIndex] else lastChar

        return adjBase + newChar
    }
}
