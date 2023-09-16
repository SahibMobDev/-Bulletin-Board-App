package com.github.sahibmobdev.bulletinboardapp.fragments

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.sahibmobdev.bulletinboardapp.R
import com.github.sahibmobdev.bulletinboardapp.databinding.SelectImageFragItemBinding

class SelectImageRvAdapter : RecyclerView.Adapter<SelectImageRvAdapter.ImageHolder>() {

    private val mainArray = ArrayList<SelectImageItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.select_image_frag_item, parent, false)
        return ImageHolder(view)

    }

    override fun getItemCount(): Int {
        return mainArray.size
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.setData(mainArray[position])
    }

    class ImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var tvTitle: TextView
        lateinit var image: ImageView
        fun setData(item: SelectImageItem) {
            tvTitle = itemView.findViewById(R.id.tvTItle)
            image = itemView.findViewById(R.id.imageContent)
            tvTitle.text = item.title
            image.setImageURI(Uri.parse(item.imageUri))
        }
    }

    fun updateAdapter(newList: List<SelectImageItem>) {
        mainArray.clear()
        mainArray.addAll(newList)
        notifyDataSetChanged()
    }
}
