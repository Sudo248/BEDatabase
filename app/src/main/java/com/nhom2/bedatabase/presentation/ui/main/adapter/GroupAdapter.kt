package com.nhom2.bedatabase.presentation.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nhom2.bedatabase.databinding.ItemGroupBinding
import com.nhom2.bedatabase.domain.models.Group

class GroupAdapter(
    private val onEditGroupClick: (pos: Int) -> Unit,
    private val onDeleteGroupClick: (pos: Int) -> Unit,
    private val onOpenGroup: (group_id: Int) -> Unit,
    private val isEnableEditAndDelete: Boolean = true
) : ListAdapter<Group, GroupAdapter.ViewHolder>(
    object: DiffUtil.ItemCallback<Group>(){
        override fun areItemsTheSame(oldItem: Group, newItem: Group) = oldItem.group_id == newItem.group_id
        override fun areContentsTheSame(oldItem: Group, newItem: Group) = oldItem == newItem
    }
){
    inner class ViewHolder(private val binding: ItemGroupBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(group: Group, position: Int){
            with(binding){
                tvNameGroup.text = group.name
                btnEditGroup.setOnClickListener {
                    onEditGroupClick(position)
                }
                btnDeleteGroup.setOnClickListener {
                    onDeleteGroupClick(position)
                }
                if (!isEnableEditAndDelete){
                    btnDeleteGroup.visibility = View.GONE
                    btnEditGroup.visibility = View.GONE
                }
                root.setOnClickListener {
                    group.group_id?.let{onOpenGroup(it)}
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }
}