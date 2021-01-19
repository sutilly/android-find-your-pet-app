package com.example.findmypet.ownerDetailFragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findmypet.R
import com.example.findmypet.liveData.LoggingObserver
import com.example.findmypet.mainFragment.MainAdapter
import com.example.findmypet.mainFragment.MainFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_owner_detail.*

@AndroidEntryPoint
class OwnerDetail : Fragment() {

    val viewModel: OwnerDetailViewModel by viewModels()
    var currentOwnerId: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_owner_detail, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycle.addObserver(LoggingObserver())

        viewModel.currentOwner.observe(viewLifecycleOwner) {
            collapsingToolbar.title = it.name
            currentOwnerId = it.id!!
        }

        viewModel.missingPets.observe({ lifecycle }) {
            val missingPetsString : String = when {
                it > 1 -> {
                    getString(R.string.petsAreMissing, it)
                }
                it == 1 -> {
                    getString(R.string.petIsMissing, it)
                }
                else -> {
                    getString(R.string.noPetIsMissing)
                }
            }
            tv_numberOfMissingPets.text = missingPetsString
        }

        // populate recycler view
        val rv = view.findViewById<RecyclerView>(R.id.rv_pets)
        rv.layoutManager = LinearLayoutManager(
                view.context, LinearLayoutManager.VERTICAL, false
        )

        viewModel.pets.observe({ lifecycle }) {
            val ownerDetailAdapter = OwnerDetailAdapter(ArrayList(it))
            rv.adapter = ownerDetailAdapter
            ownerDetailAdapter.notifyDataSetChanged()
        }

        sw_showOnlyMissingPets.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onCheckedChange(isChecked)
        }

        fab_missingPets.setOnClickListener {
            view.findNavController().navigate(
                OwnerDetailDirections.actionOwnerDetailToMapsFragment(currentOwnerId)
            )
        }
    }
}