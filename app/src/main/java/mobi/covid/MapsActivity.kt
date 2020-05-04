package mobi.covid

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import mobi.covid.util.DateHelper
import mobi.covid.util.ImageHelper


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var covidViewModel: CovidViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        covidViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(CovidViewModel::class.java)
        covidViewModel.covidList.observe(this, Observer { covidList ->
            covidList?.forEach {
                val infoText = "" + it.confirmed + " confirmed\n" +
                        it.active + " active\n" +
                        it.recovered + " recovered\n" +
                        it.deaths + " deaths\n" +
                        DateHelper().getLastDateFormatted(it.lastUpdate)

                mMap.addMarker(
                    MarkerOptions().position(LatLng(it.latitude, it.longitude))
                        .title(it.state + " " + it.country)
                        .snippet(infoText)
                        .icon(
                            ImageHelper().getBitmapDescriptorFromVector(
                                R.drawable.ic_covid_place,
                                applicationContext
                            )
                        )
                )
            }
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setInfoWindowAdapter(object : InfoWindowAdapter {
            override fun getInfoWindow(arg0: Marker): View? {
                return null
            }

            @SuppressLint("InflateParams")
            override fun getInfoContents(arg0: Marker): View {
                val v: View = layoutInflater.inflate(R.layout.info_window, null)
                v.findViewById<TextView>(R.id.title).text = arg0.title
                v.findViewById<TextView>(R.id.snippet).text = arg0.snippet
                return v
            }
        })
        covidViewModel.reloadData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        if (id == R.id.action_refresh) {
            mMap.clear()
            covidViewModel.reloadData()
            return true
        }
        if (id == R.id.action_about) {
            //TODO Implement About Screen
//            startActivity(new Intent(this, AboutActivity.class));
            Toast.makeText(applicationContext, "COVID19 App ", Toast.LENGTH_LONG).show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}