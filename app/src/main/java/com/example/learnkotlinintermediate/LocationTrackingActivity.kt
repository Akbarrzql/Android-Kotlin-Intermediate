package com.example.learnkotlinintermediate

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.example.learnkotlinintermediate.databinding.ActivityLocationTrackingBinding
import com.example.learnkotlinintermediate.utils.LocationPermissionHelper
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import java.lang.ref.WeakReference

class LocationTrackingActivity: AppCompatActivity(){

     private lateinit var locationPermissionHelper: LocationPermissionHelper
     private lateinit var binding: ActivityLocationTrackingBinding

     private val onIndicatorBearingChangedListener = OnIndicatorBearingChangedListener{
         binding.mapViewUserLocation.getMapboxMap().setCamera(
             CameraOptions.Builder().bearing(it).build()
         )
     }

    private val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
        binding.mapViewUserLocation.getMapboxMap().setCamera(CameraOptions.Builder().center(it).build())
        binding.mapViewUserLocation.gestures.focalPoint = binding.mapViewUserLocation.getMapboxMap().pixelForCoordinate(it)
    }

    private val onMoveListener = object : OnMoveListener {
        override fun onMove(detector: MoveGestureDetector): Boolean {
            return false
        }

        override fun onMoveBegin(detector: MoveGestureDetector) {
            onCameraTrackingDismissed()
        }

        override fun onMoveEnd(detector: MoveGestureDetector) {}

    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityLocationTrackingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        locationPermissionHelper = LocationPermissionHelper(WeakReference(this))
        locationPermissionHelper.checkPermissions {
            onMapReady()
        }

    }

    private fun onMapReady() {
        binding.mapViewUserLocation.getMapboxMap().setCamera(
            CameraOptions.Builder()
                .zoom(9.0)
                .build()
        )
        if (resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK == android.content.res.Configuration.UI_MODE_NIGHT_NO) {
            binding.mapViewUserLocation.getMapboxMap().loadStyleUri(
                Style.TRAFFIC_DAY
            ) {
                initLocationComponent()
                setupGesturesListener()
            }
        } else {
            binding.mapViewUserLocation.getMapboxMap().loadStyleUri(
                Style.TRAFFIC_NIGHT
            ) {
                initLocationComponent()
                setupGesturesListener()
            }
        }
    }

    private fun moveToUserLocation() {
        val locationComponentPlugin = binding.mapViewUserLocation.location
        locationComponentPlugin.addOnIndicatorPositionChangedListener { location ->
            val cameraOptions = CameraOptions.Builder()
                .center(location)
                .build()
            binding.mapViewUserLocation.getMapboxMap().setCamera(cameraOptions)
            binding.mapViewUserLocation.gestures.focalPoint = binding.mapViewUserLocation.getMapboxMap().pixelForCoordinate(location)
        }
    }


    private fun setupGesturesListener() {
        binding.mapViewUserLocation.gestures.addOnMoveListener(onMoveListener)
    }

    private fun initLocationComponent() {
        val locationComponentPlugin = binding.mapViewUserLocation.location
        locationComponentPlugin.updateSettings {
            this.enabled = true
            this.pulsingEnabled = true
            this.locationPuck = LocationPuck2D(
                bearingImage = AppCompatResources.getDrawable(
                    this@LocationTrackingActivity,
                    R.drawable.mapbox_user_puck_icon,
                ),
                shadowImage = AppCompatResources.getDrawable(
                    this@LocationTrackingActivity,
                    R.drawable.mapbox_user_icon_shadow,
                ),
                scaleExpression = interpolate {
                    linear()
                    zoom()
                    stop {
                        literal(0.0)
                        literal(0.6)
                    }
                    stop {
                        literal(20.0)
                        literal(1.0)
                    }
                }.toJson()
            )
        }
        locationComponentPlugin.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
    }

    private fun onCameraTrackingDismissed() {
        Toast.makeText(this, "onCameraTrackingDismissed", Toast.LENGTH_SHORT).show()
        binding.mapViewUserLocation.location
            .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
         binding.mapViewUserLocation.location
            .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        binding.mapViewUserLocation.gestures.removeOnMoveListener(onMoveListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapViewUserLocation.location
            .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        binding.mapViewUserLocation.location
            .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        binding.mapViewUserLocation.gestures.removeOnMoveListener(onMoveListener)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    
}