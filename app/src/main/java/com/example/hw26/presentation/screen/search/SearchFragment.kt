package com.example.hw26.presentation.screen.search

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hw26.databinding.FragmentSearchBinding
import com.example.hw26.presentation.base.BaseFragment
import com.example.hw26.presentation.base.showSnackBar
import com.example.hw26.presentation.event.SearchEvent
import com.example.hw26.presentation.screen.search.adapter.SearchFilterRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask


@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {
    private val viewModel: SearchItemViewModel by viewModels()
    private val searchItemRecyclerAdapter = SearchFilterRecyclerViewAdapter()

    override fun setUp() {
        with(binding.recyclerFilteredItems) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchItemRecyclerAdapter
        }
    }

    override fun setUpListeners() {
        binding.searchViewFilter.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            var timer = Timer()
            val delay : Long = 1000

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                timer.cancel()
                timer.purge()
                timer = Timer()
                timer.schedule(object : TimerTask() {
                    override fun run() {
                        newText?.let {
                            viewModel.onEvent(SearchEvent.GetSearchedItemsEvent(it))
                        }
                    }
                }, delay)
                return true
            }
        })
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchItemsStateFlow.collect {
                    handleState(it)
                }
            }
        }
    }

    private fun handleState(state: SearchItemState) = with(state) {
        filteredItems?.let {
            searchItemRecyclerAdapter.submitList(it)
        }

        binding.progressBar.isVisible = isLoading

        error?.let {
            requireView().showSnackBar(it)
            viewModel.onEvent(SearchEvent.ResetError)
        }
    }

}