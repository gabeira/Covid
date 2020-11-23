package mobi.covid.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mobi.covid.data.model.Covid
import mobi.covid.util.DateHelper
import java.io.InputStreamReader
import java.io.StringReader
import kotlin.coroutines.CoroutineContext


class CovidRepository(private val ctx: Context, private val coroutineContext: CoroutineContext) {

    val covidLiveData: MutableLiveData<List<Covid>> = MutableLiveData()
    val networkErrors = MutableLiveData<String>()
    private val covidInfoList = mutableListOf<Covid>()

    /**
     * Load Data from CSSE at Johns Hopkins University
     * https://github.com/CSSEGISandData/COVID-19/
     */
    fun loadCovidInfoList() {
        covidInfoList.clear()
        GlobalScope.launch(coroutineContext) {
            covidLiveData.value = getWebList(ctx)
        }
    }

    private suspend fun getWebList(ctx: Context): List<Covid> {
        val day = DateHelper().getYesterdayDate()
        val urlLatestReport =
            "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports/$day.csv"
        return withContext(Dispatchers.IO) {
            try {
                val data = NetworkRequest().requestUrlDataString(urlLatestReport)
                val reader = CSVReader(StringReader(data))
                reader.drop(1).forEach {
                    addCovidInfoToList(it)
                }
                covidInfoList
            } catch (e: Exception) {
                Log.e("Covid", e.localizedMessage ?: "Error loading from Remote data $day.csv")
                getLocalList(ctx)
            }
        }
    }

    private fun getLocalList(ctx: Context): List<Covid> {
        try {
            val localDataFile = ctx.assets.open("11-22-2020.csv")
            val reader = CSVReader(InputStreamReader(localDataFile))
            reader.drop(1).forEach {
                addCovidInfoToList(it)
            }
        } catch (e: Exception) {
            Log.e("Covid", e.localizedMessage ?: "Error loading local file 11-22-2020.csv")
        }
        return covidInfoList
    }

    private fun addCovidInfoToList(it: Array<String>) {
        try {
            covidInfoList.add(
                Covid(
                    fips = it[0],
                    admin = it[1],
                    state = it[2],
                    country = it[3],
                    lastUpdate = it[4],
                    latitude = it[5].toDouble(),
                    longitude = it[6].toDouble(),
                    confirmed = it[7].toLong(),
                    deaths = it[8].toLong(),
                    recovered = it[9].toLong(),
                    active = it[10].toLong(),
                    combinedKey = it[11]
                )
            )
        } catch (e: Exception) {
            Log.w("Covid", it.contentToString() + " - " + e.localizedMessage)
        }
    }
}