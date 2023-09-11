package com.github.sahibmobdev.bulletinboardapp.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.sahibmobdev.bulletinboardapp.activities.EditAdsAct
import com.github.sahibmobdev.bulletinboardapp.databinding.SpListItemBinding

class RcViewDialogSpinnerAdapter(var dialog: AlertDialog, var tvSelection: TextView) : RecyclerView.Adapter<RcViewDialogSpinnerAdapter.SpViewHolder>() {

    private val mainList = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpViewHolder {
        val view = SpListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpViewHolder(view, dialog, tvSelection)
    }

    override fun getItemCount(): Int {
        return mainList.size
    }

    override fun onBindViewHolder(holder: SpViewHolder, position: Int) {
        holder.setData(mainList[position])
    }

    inner class SpViewHolder(private val binding: SpListItemBinding, var dialog: AlertDialog, tvSelection: TextView) : RecyclerView.ViewHolder(binding.root), OnClickListener {

        private var itemText = ""
        fun setData(text: String) {
            binding.tvSpItem.text = text
            itemText = text
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            tvSelection.text = itemText
            dialog.dismiss()
        }
    }

    fun updateAdapter(list: ArrayList<String>) {
        mainList.clear()
        mainList.addAll(list)
        notifyDataSetChanged()
    }

}