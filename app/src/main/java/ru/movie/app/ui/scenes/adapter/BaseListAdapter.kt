@file:Suppress("DiffUtilEquals")

package ru.movie.app.ui.scenes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ru.movie.app.ui.model.ItemModel

class BaseListAdapter<VB : ViewBinding, T : ItemModel>(
    private val viewInflater: (LayoutInflater, ViewGroup, Boolean) -> VB,
    private val bindFunction: (VB, T) -> Unit
) : ListAdapter<T, BaseListAdapter.BaseViewHolder<VB, T>>(BaseDiffUtilItemCallback<T>()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB, T> {
        val binding = viewInflater(LayoutInflater.from(parent.context), parent, false)
        return BaseViewHolder(binding, bindFunction)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<VB, T>, position: Int) =
        holder.run { bind(getItem(position)) }

    class BaseDiffUtilItemCallback<T : ItemModel> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: T, newItem: T) = oldItem == newItem
    }

    class BaseViewHolder<VB : ViewBinding, T : ItemModel>(
        private val binding: VB,
        private val bindFunction: (VB, T) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemModel: T) {
            bindFunction(binding, itemModel)
        }
    }
}

