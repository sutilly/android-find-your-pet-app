package com.example.findmypet.mainFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findmypet.R
import com.example.findmypet.dependencyInjection.RepoStatus
import com.example.findmypet.liveData.LoggingObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class MainFragment : Fragment() {

    val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycle.addObserver(LoggingObserver())

        swipeRefresh.setOnRefreshListener {
            // disable circular loader
            swipeRefresh.isRefreshing = false
            viewModel.onRefreshData()
        }

        viewModel.repoStatus.observe(viewLifecycleOwner) {
            // disable circular loader
            if (it == RepoStatus.IN_PROGRESS) {
                progressBar.visibility = ProgressBar.VISIBLE
            } else {
                progressBar.visibility = ProgressBar.INVISIBLE
            }
        }

        val rv = view.findViewById<RecyclerView>(R.id.rv_owners)

        viewModel.allOwnersLiveData.observe({lifecycle}) {
            val mainAdapter = MainAdapter(ArrayList(it))
            rv.adapter = mainAdapter
            mainAdapter.notifyDataSetChanged()
        }

        rv.layoutManager = LinearLayoutManager(
            view.context, LinearLayoutManager.VERTICAL, false)

    }

}