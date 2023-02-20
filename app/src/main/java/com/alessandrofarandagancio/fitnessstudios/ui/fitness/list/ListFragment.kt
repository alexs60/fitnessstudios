package com.alessandrofarandagancio.fitnessstudios.ui.fitness.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alessandrofarandagancio.fitnessstudios.databinding.FragmentListBinding
import com.alessandrofarandagancio.fitnessstudios.models.Business
import com.alessandrofarandagancio.fitnessstudios.ui.fitness.FitnessViewModel
import com.alessandrofarandagancio.fitnessstudios.ui.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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
            if(it.status == Status.SUCCESS){
                businessAdapter.submitList(it.data!!.businesses)
            }
        })

        return root
    }

    private fun adapterOnClick(business: Business) {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}