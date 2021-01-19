package com.example.findmypet.mapsFragment

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.findmypet.R
import com.example.findmypet.database.Owner

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsFragment : Fragment(), GoogleMap.OnMarkerClickListener {

    val viewModel: MapViewModel by viewModels()

    @SuppressLint("PotentialBehaviorOverride")
    private val callback = OnMapReadyCallback { googleMap ->
        setOwnerMarker(googleMap)
        setPetsMarker(googleMap)
        googleMap.setOnMarkerClickListener(this)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

    }

    private fun setOwnerMarker(googleMap: GoogleMap) {
        viewModel.currentOwner.observe({ lifecycle }) {
            val currentOwnerPosition = LatLng(it.latitude, it.longitude)

            googleMap.addMarker(MarkerOptions()
                    .position(currentOwnerPosition)
                    .title(getString(R.string.mapUser,it.name))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_user)))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentOwnerPosition, 12F))
        }
    }

    private fun setPetsMarker(googleMap: GoogleMap) {
        viewModel.pets.observe({ lifecycle }) {
            it.forEach { pet ->
                val petLocation = LatLng(pet.latitude, pet.longitude)

                if (pet.isMissing) {
                    googleMap.addMarker(MarkerOptions()
                            .position(petLocation)
                            .title(getString(R.string.XIsMissing, pet.name))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_pet_missing)))
                } else {
                    googleMap.addMarker(MarkerOptions()
                            .position(petLocation)
                            .title(getString(R.string.XIsNotMissing, pet.name))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_pet_found)))
                }
            }
        }
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        if (marker != null) {
            Toast.makeText(activity, marker.title!!, Toast.LENGTH_SHORT).show()
        }
        return true
    }
}