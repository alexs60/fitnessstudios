package com.alessandrofarandagancio.fitnessstudios.ui.fitness.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alessandrofarandagancio.fitnessstudios.R
import com.alessandrofarandagancio.fitnessstudios.models.yelp.Business

class BusinessItemAdapter(private val onClick: (Business) -> Unit) :
    ListAdapter<Business, BusinessItemAdapter.BusinessViewHolder>(SchoolDiffCallback) {

    class BusinessViewHolder(view: View, val onClick: (Business) -> Unit) :
        RecyclerView.ViewHolder(view) {
        private val textViewName: TextView = view.findViewById(R.id.name)
        private val textViewAddress: TextView = view.findViewById(R.id.address)
        private lateinit var currentBusiness: Business

        init {
            view.setOnClickListener {
                onClick(currentBusiness)
            }
        }

        fun bind(business: Business) {
            currentBusiness = business
            textViewName.text = business.name
            textViewAddress.text = business.location.displayAddress.joinToString()
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BusinessViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.business_item, viewGroup, false)

        return BusinessViewHolder(view, onClick)
    }

    override fun onBindViewHolder(viewHolder: BusinessViewHolder, position: Int) {
        val school = getItem(position)
        viewHolder.bind(school)
    }
}

object SchoolDiffCallback : DiffUtil.ItemCallback<Business>() {
    override fun areItemsTheSame(oldItem: Business, newItem: Business): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Business, newItem: Business): Boolean {
        return oldItem.id == newItem.id
    }
}
