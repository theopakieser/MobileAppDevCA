package org.wit.bookapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.bookapp.R
import org.wit.bookapp.databinding.ActivityMapBinding
import org.wit.bookapp.models.Location

class MapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private lateinit var map: GoogleMap
    private var location = Location()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        location = intent.getParcelableExtra("location") ?: Location()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val pos = LatLng(location.lat, location.lng)

        val marker = map.addMarker(
            MarkerOptions()
                .position(pos)
                .draggable(true)
                .title("Book Location")
        )

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, location.zoom))
        map.setOnMarkerDragListener(this)
    }

    override fun onMarkerDragEnd(marker: Marker) {
        location.lat = marker.position.latitude
        location.lng = marker.position.longitude
        location.zoom = map.cameraPosition.zoom
    }

    override fun onMarkerDrag(marker: Marker) {}
    override fun onMarkerDragStart(marker: Marker) {}

    override fun onBackPressed() {
        val resultIntent = Intent()
        resultIntent.putExtra("location", location)
        setResult(RESULT_OK, resultIntent)
        finish()

        super.onBackPressed()
    }
}
