package com.alessandrofarandagancio.fitnessstudios.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.alessandrofarandagancio.fitnessstudios.R
import com.alessandrofarandagancio.fitnessstudios.databinding.ActivityMainBinding
import com.alessandrofarandagancio.fitnessstudios.ui.fitness.FitnessViewModel
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private lateinit var binding: ActivityMainBinding

    private val fitnessViewModel: FitnessViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavController()
    }

    override fun onResume() {
        super.onResume()

        binding.loading.visibility = View.VISIBLE
        checkAndRequestPermission()

        fitnessViewModel.businessResponse.observe(this) {
            binding.loading.visibility = View.GONE
            fitnessViewModel.businessResponse.removeObservers(this)
        }

        mainViewModel.toShowPage.observe(this) {
            binding.navView.selectedItemId = it
            navController.addOnDestinationChangedListener(this)
            mainViewModel.toShowPage.removeObservers(this)
        }

    }

    private fun setupNavController() {
        navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.MapFragment,
                R.id.ListFragment,
                R.id.DetailFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
    }

    private fun checkAndRequestPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        } else {
            getLastKnownLocation();
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    fitnessViewModel.setNewLocation(location.latitude, location.longitude);
                } else {
                    fitnessViewModel.noLocationProvided()
                }
            }
    }

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
            }
            else -> {
                // No location access granted.
            }
        }
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        Log.d("Last onDestinationChanged Called", "${destination.id}")
        when (destination.id) {
            R.id.MapFragment,
            R.id.ListFragment -> handleOnTier1(destination.id)
            else -> handleOnTier2()
        }


    }

    private fun handleOnTier1(id: Int) {
        mainViewModel.setLastPage(id)
        binding.navView.visibility = View.VISIBLE

    }

    private fun handleOnTier2() {
        binding.navView.visibility = View.GONE
    }

}