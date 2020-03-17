package mobi.covid.data

import android.content.Context
import android.util.Log
import com.opencsv.CSVReader
import mobi.covid.data.model.Covid
import java.io.InputStreamReader
import java.lang.Exception


class CovidRepository {

    //https://github.com/CSSEGISandData/COVID-19/
    fun getCovidInfoList(ctx: Context): List<Covid> {
        return getLocalList(ctx)
    }

    private fun getLocalList(ctx: Context): List<Covid> {
        val covidInfoList = mutableListOf<Covid>()
        try {
            val localDataFile = ctx.assets.open("03-16-2020.csv")
            val reader = CSVReader(InputStreamReader(localDataFile))
            reader.drop(1).forEach {
                try {
                    covidInfoList.add(
                        Covid(
                            state = it[0],
                            country = it[1],
                            lastUpdate = it[2],
                            confirmed = it[3].toLong(),
                            deaths = it[4].toLong(),
                            recovered = it[5].toLong(),
                            latitude = it[6].toDouble(),
                            longitude = it[7].toDouble()
                        )
                    )
                } catch (e: Exception) {
                    Log.e("Covid", e.localizedMessage ?: "Error loading local file converting data")
                }
            }
        } catch (e: Exception) {
            Log.e("Covid", e.localizedMessage ?: "Error loading local file")
        }
        return covidInfoList
    }
}