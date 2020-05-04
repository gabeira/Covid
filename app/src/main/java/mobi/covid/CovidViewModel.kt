package mobi.covid

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import mobi.covid.data.CovidRepository
import mobi.covid.data.model.Covid
import kotlin.coroutines.CoroutineContext

class CovidViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    private val repository: CovidRepository
    var covidList: LiveData<List<Covid>>

    init {
        repository = CovidRepository(application.applicationContext, coroutineContext)
        covidList = repository.covidLiveData
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    fun reloadData() {
        repository.loadCovidInfoList()
    }

    fun getNetworkErrors() = repository.networkErrors
}