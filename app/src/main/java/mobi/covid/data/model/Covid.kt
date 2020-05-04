package mobi.covid.data.model

data class Covid(
    val fips: String,
    val admin: String,
    val state: String,
    val country: String,
    val lastUpdate: String,
    val confirmed: Long,
    val deaths: Long,
    val recovered: Long,
    val latitude: Double,
    val longitude: Double,
    val active: Long,
    val combinedKey: String
)