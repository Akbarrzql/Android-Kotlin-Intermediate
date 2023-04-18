package com.example.learnkotlinintermediate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.learnkotlinintermediate.databinding.ActivityMainBinding
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.locationcomponent.location

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS)

        binding.mapView.getMapboxMap().loadStyleUri(
            Style.MAPBOX_STREETS
        ) {
            binding.mapView.location.updateSettings {
                enabled = true
                pulsingEnabled = true
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }
}