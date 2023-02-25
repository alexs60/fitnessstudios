package com.alessandrofarandagancio.fitnessstudios.ui.fitness.map

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.alessandrofarandagancio.fitnessstudios.R
import com.alessandrofarandagancio.fitnessstudios.constant.baseZoomShared
import com.alessandrofarandagancio.fitnessstudios.constant.defLatitude
import com.alessandrofarandagancio.fitnessstudios.constant.defLongitude
import com.alessandrofarandagancio.fitnessstudios.databinding.FragmentMapBinding
import com.alessandrofarandagancio.fitnessstudios.models.yelp.Business
import com.alessandrofarandagancio.fitnessstudios.ui.fitness.FitnessViewModel
import com.alessandrofarandagancio.fitnessstudios.ui.utils.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private val fitnessViewModel: FitnessViewModel by activityViewModels()

    private var _binding: FragmentMapBinding? = null

    private lateinit var map: GoogleMap

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding.let { _binding }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMapBinding.inflate(inflater, container, false)

        val root = binding?.root

        val googleMap = childFragmentManager.findFragmentById(R.id.googleMap) as SupportMapFragment
        googleMap.getMapAsync(this)

        return root
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        configureMapUI()
        observeForMyLocation()
        observeForData()
    }

    @SuppressLint("MissingPermission")
    private fun configureMapUI() {
        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isMyLocationButtonEnabled = true
        map.isMyLocationEnabled = true
        map.uiSettings.isCompassEnabled = true
        map.uiSettings.isMapToolbarEnabled = true

    }

    private fun observeForMyLocation() {
        map.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                fitnessViewModel.getCurrentLocation(),
                baseZoomShared
            )
        )

        fitnessViewModel.locationUpdate.observe(viewLifecycleOwner) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(it, 13f))
        }
    }

    @SuppressLint("PotentialBehaviorOverride")
    private fun observeForData() {
        map.clear()
        fitnessViewModel.businessResponse.observe(viewLifecycleOwner) {
            var lastLocation = LatLng(defLatitude, defLongitude)
            if (::map.isInitialized && it.status == Status.SUCCESS) {
                it.data.businesses.forEach { business: Business ->
                    lastLocation = LatLng(
                        business.coordinates.latitude,
                        business.coordinates.longitude
                    )
                    val marker = map.addMarker(
                        MarkerOptions()
                            .position(lastLocation)
                            .title(business.name)
                    )
                    marker?.let { marker.tag = business.alias }
                }
            }
        }
        map.setOnInfoWindowClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onInfoWindowClick(marker: Marker) {
        fitnessViewModel.goToDetails(
            this,
            R.id.action_MapFragment_to_DetailsFragment,
            marker.tag.toString()
        )
    }
}