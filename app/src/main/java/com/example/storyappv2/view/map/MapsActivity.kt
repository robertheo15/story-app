package com.example.storyappv2.view.map

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.storyappv2.R
import com.example.storyappv2.databinding.ActivityMapsBinding
import com.example.storyappv2.view.story.StoryViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MapsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapsBinding
    private val storyViewModel: StoryViewModel by viewModels()


    private val callback = OnMapReadyCallback { googleMap ->
        lifecycleScope.launch {
            storyViewModel.getData().forEach {
                val location = LatLng(it.lat, it.lon)
                googleMap.uiSettings.isZoomControlsEnabled = true
                try {
                    val success =
                        googleMap.setMapStyle(
                            MapStyleOptions.loadRawResourceStyle(
                                applicationContext,
                                R.raw.map_style
                            )
                        )
                    if (!success) {
                        Log.e(TAG, "Style parsing failed.")
                    }
                } catch (exception: Resources.NotFoundException) {
                    Log.e(TAG, "Can't find style. Error: ", exception)
                }
                googleMap.addMarker(MarkerOptions().position(location).title(it.name))
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(location))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(callback)
    }

    companion object {
        private const val TAG = "MapsActivity"
    }
}