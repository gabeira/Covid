package mobi.covid.data.model

data class Covid(
    val state: String,
    val country: String,
    val lastUpdate: String,
    val confirmed: Long,
    val deaths: Long,
    val recovered: Long,
    val latitude: Double,
    val longitude: Double
)