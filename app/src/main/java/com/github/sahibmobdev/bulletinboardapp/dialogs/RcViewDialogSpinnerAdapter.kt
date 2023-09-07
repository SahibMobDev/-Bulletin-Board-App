package com.github.sahibmobdev.bulletinboardapp.dialogs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.sahibmobdev.bulletinboardapp.databinding.SpListItemBinding

class RcViewDialogSpinnerAdapter : RecyclerView.Adapter<RcViewDialogSpinnerAdapter.SpViewHolder>() {

    private val mainList = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpViewHolder {
        val view = SpListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mainList.size
    }

    override fun onBindViewHolder(holder: SpViewHolder, position: Int) {
        holder.setData(mainList[position])
    }

    inner class SpViewHolder(private val binding: SpListItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun setData(text: String) {
            binding.tvSpItem.text = text
        }
    }

    fun updateAdapter(list: ArrayList<String>) {
        mainList.clear()
        mainList.addAll(list)
        notifyDataSetChanged()
    }

}