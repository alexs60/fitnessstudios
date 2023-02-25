package com.alessandrofarandagancio.fitnessstudios.ui.fitness.map

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.net.toUri
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import com.alessandrofarandagancio.fitnessstudios.R
import com.alessandrofarandagancio.fitnessstudios.constant.baseZoomShared
import com.alessandrofarandagancio.fitnessstudios.databinding.FragmentDetailBinding
import com.alessandrofarandagancio.fitnessstudios.ui.fitness.FitnessViewModel
import com.alessandrofarandagancio.fitnessstudios.ui.fitnessdetail.NavigationViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.Polygon
import com.google.android.gms.maps.model.PolygonOptions
import com.google.maps.android.PolyUtil
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment(), OnMapReadyCallback {

    private val navigationViewModel: NavigationViewModel by activityViewModels()
    private val fitnessViewModel: FitnessViewModel by activityViewModels()

    private var _binding: FragmentDetailBinding? = null

    private lateinit var mapNavigation: GoogleMap

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)


        val root: View = binding.root

        val googleMapNavigation =
            childFragmentManager.findFragmentById(R.id.googleMapNavigation) as SupportMapFragment
        googleMapNavigation.getMapAsync(this)
        addMenu()
        loadFitnesDetailAndImage()

        return root
    }

    private fun loadFitnesDetailAndImage() {
        val imageUrl = arguments?.let { arguments?.getString("img") }
        val imgUri = imageUrl.let { imageUrl?.toUri()?.buildUpon()?.scheme("https")?.build() }
        binding.image.load(imgUri)
        binding.fitnessDetail.apply {
            text = arguments?.getString("name")
        }
    }

    private fun addMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.details_fragment_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return if (menuItem.itemId == R.id.share) {
                    Toast.makeText(activity, "share content", Toast.LENGTH_SHORT).show()
                    true
                } else false
            }
        }, viewLifecycleOwner)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mapNavigation = googleMap
        configureMapUI()
        //observeForDirections()
        observeForDirectionsPoints()
        prepareDirection()
    }

    private fun prepareDirection() {
        val origin = fitnessViewModel.getCurrentLocation()
        val destination = arguments?.getString("destination")
        destination?.let {
            navigationViewModel.getDirections(origin, it)
        }
    }

    @SuppressLint("MissingPermission")
    private fun configureMapUI() {
        mapNavigation.uiSettings.isZoomControlsEnabled = true
        mapNavigation.uiSettings.isMyLocationButtonEnabled = true
        mapNavigation.isMyLocationEnabled = true
        mapNavigation.isMyLocationEnabled = true
        mapNavigation.uiSettings.isCompassEnabled = true
        mapNavigation.uiSettings.isMapToolbarEnabled = true
        mapNavigation.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                fitnessViewModel.getCurrentLocation(),
                baseZoomShared
            )
        )
    }

    private fun observeForDirectionsPoints() {
        navigationViewModel.directionsResponsePair.observe(viewLifecycleOwner) {
            it.data.forEach() { it1 ->
                val pointList = PolyUtil.decode(it1)
                val polygon = mapNavigation.addPolygon(
                    PolygonOptions()
                        .clickable(true)
                        .addAll(pointList)
                        .addAll(pointList.reversed())
                        .strokeWidth(20F)
                        .fillColor(R.color.purple_800)
                        .strokeColor(R.color.purple_800)
                        .strokeJointType(JointType.DEFAULT)
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}