package com.alessandrofarandagancio.fitnessstudios.ui.fitness.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.alessandrofarandagancio.fitnessstudios.R
import com.alessandrofarandagancio.fitnessstudios.databinding.FragmentListBinding
import com.alessandrofarandagancio.fitnessstudios.models.yelp.Business
import com.alessandrofarandagancio.fitnessstudios.ui.fitness.FitnessViewModel
import com.alessandrofarandagancio.fitnessstudios.ui.utils.Status
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private val fitnessViewModel: FitnessViewModel by activityViewModels()

    private var _binding: FragmentListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var businessAdapter = BusinessItemAdapter { business -> adapterOnClick(business) }
        binding.recyclerView.adapter = businessAdapter

        fitnessViewModel.businessResponse.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS) {
                businessAdapter.submitList(it.data.businesses)
            }
        })

        return root
    }

    override fun onResume() {
        super.onResume()
    }

    private fun adapterOnClick(business: Business) {
        if (business.coordinates == null) {
            Snackbar.make(binding.root, R.string.no_coords, Snackbar.LENGTH_SHORT)
            return
        }
        fitnessViewModel.goToDetails(this, R.id.action_ListFragment_to_DetailsFragment, business);
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}