package com.nhom2.bedatabase.presentation.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nhom2.bedatabase.databinding.ItemVocabularyBinding
import com.nhom2.bedatabase.domain.models.Eng


class VocabularyAdapter(
    private val onEditEngClick: (eng_id: Int) -> Unit,
    private val onDeleteEngClick: (eng_id: Int) -> Unit,
    private val onEngClick: (eng_id: Int) -> Unit
) : ListAdapter<Eng, VocabularyAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<Eng>(){
        override fun areItemsTheSame(oldItem: Eng, newItem: Eng): Boolean = oldItem.eng_id == newItem.eng_id
        override fun areContentsTheSame(oldItem: Eng, newItem: Eng): Boolean = oldItem == newItem
    }
) {

    inner class ViewHolder(private val binding: ItemVocabularyBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(eng: Eng){
            with(binding){
                tvNameVocabulary.text = eng.content
                tvMeanVocabulary.text = eng.vns[0].content
                tvVocabularyPronunciation.text = eng.pronunciation
                btnEditVocabulary.setOnClickListener{
                    onEditEngClick(eng.eng_id!!)
                }
                btnDeleteVocabulary.setOnClickListener {
                    onDeleteEngClick(eng.eng_id!!)
                }

                root.setOnClickListener {
                    onEngClick(eng.eng_id!!)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemVocabularyBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

}