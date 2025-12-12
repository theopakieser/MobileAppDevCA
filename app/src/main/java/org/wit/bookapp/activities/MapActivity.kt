package org.wit.bookapp.activities


import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.bookapp.R

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap


    private val bookstores = listOf(
        LatLng(53.3438, -6.2546), // Trinity College Library
        LatLng(53.3498, -6.2603), // Hodges Figgis
        LatLng(53.3441, -6.2675), // Dublin City Library
        LatLng(53.3478, -6.2597), // Books Upstairs
        LatLng(53.3416, -6.2569), // National Library of Ireland
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val toolbar = findViewById<Toolbar>(R.id.mapToolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val dublin = LatLng(53.3438, -6.2546)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(dublin, 14f))

        bookstores.forEach {
            map.addMarker(
                MarkerOptions()
                    .position(it)
                    .title("Bookstore / Library")
            )
        }

        val searchField = findViewById<EditText>(R.id.searchText)
        val searchButton = findViewById<Button>(R.id.btnSearch)

        searchButton.setOnClickListener {
            val query = searchField.text.toString()
            if (query.isNotEmpty()){
                geoLocate(query)
            }
        }
    }


    private fun geoLocate(query: String){
        try{
            val geocoder = Geocoder(this)
            val results = geocoder.getFromLocationName(query, 1)

            if (!results.isNullOrEmpty()){
                val location = LatLng(results[0].latitude, results[0].longitude)

                map.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
                map.addMarker(
                    MarkerOptions().position(location).title(query)
                )
            }
        } catch (e : Exception){
            e.printStackTrace()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
