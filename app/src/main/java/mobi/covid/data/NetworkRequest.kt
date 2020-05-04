package mobi.covid.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request.Builder
import java.io.IOException

class NetworkRequest {

    var client = OkHttpClient()

    @Throws(IOException::class)
    suspend fun requestUrlDataString(url: String): String = withContext(Dispatchers.IO) {
        val request = Builder().url(url).build()
        client.newCall(request).execute().use { response ->
            return@withContext response.body?.string() ?: ""
        }
    }
}