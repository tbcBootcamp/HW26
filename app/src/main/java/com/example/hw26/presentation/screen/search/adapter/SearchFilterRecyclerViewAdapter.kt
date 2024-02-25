package com.example.hw26.presentation.screen.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hw26.databinding.SearchItemBinding
import com.example.hw26.presentation.model.Dot
import com.example.hw26.presentation.model.SearchItemUiModel

class SearchFilterRecyclerViewAdapter :
    ListAdapter<SearchItemUiModel, SearchFilterRecyclerViewAdapter.SearchItemsViewHolder>(filterDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemsViewHolder {
        return SearchItemsViewHolder(SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SearchItemsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class SearchItemsViewHolder(private val binding: SearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: SearchItemUiModel) {
            val item = currentList[adapterPosition]
            with(binding) {
                if (item.numberOfChildren > 0) {
                    recyclerViewDots.visibility = View.VISIBLE
                    recyclerViewDots.layoutManager = LinearLayoutManager(itemView.context, RecyclerView.HORIZONTAL, false)
                    recyclerViewDots.adapter = DotsRecyclerViewAdapter(category.numberOfChildren).apply {
                        val dots = mutableListOf<Dot>()
                        for (i in 1..item.numberOfChildren)
                            dots.add(Dot(i))
                        submitList(dots)
                    }
                }

                tvName.text = item.name
            }
        }
    }

    companion object {
        private val filterDiffCallback = object : DiffUtil.ItemCallback<SearchItemUiModel>() {

            override fun areItemsTheSame(oldItem: SearchItemUiModel, newItem: SearchItemUiModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SearchItemUiModel, newItem: SearchItemUiModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}