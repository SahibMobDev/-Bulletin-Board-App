package com.github.sahibmobdev.bulletinboardapp.fragments

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.sahibmobdev.bulletinboardapp.R
import com.github.sahibmobdev.bulletinboardapp.activities.EditAdsAct
import com.github.sahibmobdev.bulletinboardapp.databinding.SelectImageFragItemBinding
import com.github.sahibmobdev.bulletinboardapp.utils.AdapterCallback
import com.github.sahibmobdev.bulletinboardapp.utils.ImageManager
import com.github.sahibmobdev.bulletinboardapp.utils.ImagePicker
import com.github.sahibmobdev.bulletinboardapp.utils.ItemTouchMoveCallback

class SelectImageRvAdapter(val adapterCallback: AdapterCallback) : RecyclerView.Adapter<SelectImageRvAdapter.ImageHolder>(),
    ItemTouchMoveCallback.ItemTouchAdapter {

    val mainArray = ArrayList<Bitmap>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val binding = SelectImageFragItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageHolder(binding, parent.context, this)

    }

    override fun getItemCount(): Int {
        return mainArray.size
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.setData(mainArray[position])
    }

    override fun onMove(startPos: Int, targetPos: Int) {
        val targetItem = mainArray[targetPos]
        mainArray[targetPos] = mainArray[startPos]
        mainArray[startPos] = targetItem
        notifyItemMoved(startPos, targetPos)
    }

    override fun onClear() {
        notifyDataSetChanged()
    }

    class ImageHolder(private val binding: SelectImageFragItemBinding, val context: Context, val adapter: SelectImageRvAdapter) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(bitmap: Bitmap) {

            binding.imEditImage.setOnClickListener {
                ImagePicker.getImages(
                    context as EditAdsAct,
                    1,
                    ImagePicker.REQUEST_CODE_GET_SINGLE_IMAGE
                )
                context.editImagePos = adapterPosition
            }

            binding.imDelete.setOnClickListener {
                adapter.mainArray.removeAt(adapterPosition)
                adapter.notifyItemRemoved(adapterPosition)
                for (n in 0 until adapter.mainArray.size) {
                    adapter.notifyItemChanged(n)
                }
                adapter.adapterCallback.onItemDelete()
            }

            binding.tvTitle.text = context.resources.getStringArray(R.array.title_array)[adapterPosition]
            ImageManager.chooseScaleType(binding.imageContent, bitmap)
            binding.imageContent.setImageBitmap(bitmap)
        }
    }

    fun updateAdapter(newList: List<Bitmap>, needClear: Boolean) {
        if (needClear) {
            mainArray.clear()
        }
        mainArray.addAll(newList)
        notifyDataSetChanged()
    }
}
