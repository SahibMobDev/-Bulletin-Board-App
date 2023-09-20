package com.github.sahibmobdev.bulletinboardapp.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.sahibmobdev.bulletinboardapp.databinding.ImageAdapterItemBinding
import com.github.sahibmobdev.bulletinboardapp.fragments.SelectImageItem

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ImageHolder>() {

    private val mainArray = ArrayList<SelectImageItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val view = ImageAdapterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageHolder(view)
    }

    override fun getItemCount(): Int {
        return mainArray.size
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.setData(mainArray[position].imageUri)
    }

    class ImageHolder(private val binding: ImageAdapterItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setData(uri: String) {
            binding.imItem.setImageURI(Uri.parse(uri))
        }
    }

    fun update(newList: ArrayList<SelectImageItem>) {
        mainArray.clear()
        mainArray.addAll(newList)
        notifyDataSetChanged()
    }
}
