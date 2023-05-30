package com.example.learnkotlinintermediate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.learnkotlinintermediate.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.locationcomponent.location

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MapStyle()
        OnClick()


    }

    private fun OnClick(){
        binding.fabAdd.setOnClickListener {
            MyBottomSheetDialogFragment().apply {
                show(supportFragmentManager, tag)
            }
        }
    }

    private fun MapStyle(){
        if (resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK == android.content.res.Configuration.UI_MODE_NIGHT_NO) {
            binding.mapView.getMapboxMap().loadStyleUri(
                Style.TRAFFIC_DAY
            ) {
                binding.mapView.location.updateSettings {
                    enabled = true
                    pulsingEnabled = true
                }
            }
        } else {
            binding.mapView.getMapboxMap().loadStyleUri(
                Style.TRAFFIC_NIGHT
            ) {
                binding.mapView.location.updateSettings {
                    enabled = true
                    pulsingEnabled = true
                }
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