package com.github.sahibmobdev.bulletinboardapp.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.sahibmobdev.bulletinboardapp.databinding.ImageAdapterItemBinding

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ImageHolder>() {

     val mainArray = ArrayList<Bitmap>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val view = ImageAdapterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageHolder(view)
    }

    override fun getItemCount(): Int {
        return mainArray.size
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.setData(mainArray[position])
    }

    class ImageHolder(private val binding: ImageAdapterItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setData(bitmap: Bitmap) {
            binding.imItem.setImageBitmap(bitmap)
        }
    }

    fun update(newList: ArrayList<Bitmap>) {
        mainArray.clear()
        mainArray.addAll(newList)
        notifyDataSetChanged()
    }
}
