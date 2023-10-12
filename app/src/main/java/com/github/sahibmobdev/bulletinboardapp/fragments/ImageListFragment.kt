package com.github.sahibmobdev.bulletinboardapp.fragments

import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.sahibmobdev.bulletinboardapp.R
import com.github.sahibmobdev.bulletinboardapp.databinding.ListImageFragmentBinding
import com.github.sahibmobdev.bulletinboardapp.dialoghelper.ProgressDialog
import com.github.sahibmobdev.bulletinboardapp.utils.AdapterCallback
import com.github.sahibmobdev.bulletinboardapp.utils.ImageManager
import com.github.sahibmobdev.bulletinboardapp.utils.ImagePicker
import com.github.sahibmobdev.bulletinboardapp.utils.ImagePicker.MAX_IMAGE_COUNT
import com.github.sahibmobdev.bulletinboardapp.utils.ItemTouchMoveCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ImageListFragment(private val fragCloseInterface: FragmentCloseInterface, private val newList: ArrayList<String>?) : BaseAdsFragment(), AdapterCallback {

    val adapter = SelectImageRvAdapter(this)
    val dragCallback = ItemTouchMoveCallback(adapter)
    val touchHelper = ItemTouchHelper(dragCallback)
    var job: Job? = null
    private var addImageItem: MenuItem? = null

    lateinit var binding: ListImageFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListImageFragmentBinding.inflate(inflater)
        adView = binding.adView
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        binding.apply {
            touchHelper.attachToRecyclerView(rcViewSelectImage)
            rcViewSelectImage.layoutManager = LinearLayoutManager(activity)
            rcViewSelectImage.adapter = adapter
        }

        if (newList != null) {
            resizeSelectedImages(newList, true)
        }

    }

    override fun onItemDelete() {
        addImageItem?.isVisible = true
    }

    fun updateAdapterFromEdit(bitmapList: List<Bitmap>) {
        adapter.updateAdapter(bitmapList, true)
    }

    override fun onDetach() {
        super.onDetach()
        fragCloseInterface.onFragClose(adapter.mainArray)
        job?.cancel()
    }

    override fun onClose() {
        super.onClose()
        activity?.supportFragmentManager?.beginTransaction()?.remove(this@ImageListFragment)?.commit()

    }

    private fun resizeSelectedImages(newList: ArrayList<String>, needClear: Boolean) {
        job = CoroutineScope(Dispatchers.Main).launch {
            val dialog = ProgressDialog.createProgressDialog(activity as Activity)
            val bitmapList = ImageManager.imageResize(newList)
            dialog.dismiss()
            adapter.updateAdapter(bitmapList, needClear)
            if (adapter.mainArray.size > 2) addImageItem?.isVisible = false
        }
    }

    private fun setUpToolbar() {
        binding.apply {

            tb.inflateMenu(R.menu.menu_choose_image)
            //binding.tb.navigationIcon?.mutate()?.setTint(ContextCompat.getColor(requireContext(), R.color.green_mine))
            val deleteItem = tb.menu.findItem(R.id.id_delete_image)
            addImageItem = tb.menu.findItem(R.id.id_add_image)

            tb.setNavigationOnClickListener {
                showInterAd()
            }
            deleteItem.setOnMenuItemClickListener {
                adapter.updateAdapter(ArrayList(), true)
                addImageItem?.isVisible = true
                true
            }
            addImageItem?.setOnMenuItemClickListener {
                val imageCount = MAX_IMAGE_COUNT - adapter.mainArray.size
                ImagePicker.getImages(
                    activity as AppCompatActivity,
                    imageCount,
                    ImagePicker.REQUEST_CODE_GET_IMAGES
                )
                true
            }
        }
    }

    fun updateAdapter(newList: ArrayList<String>) {
        resizeSelectedImages(newList, false)
    }

    fun setSingleImage(uri: String, pos: Int) {
        val pBar = binding.rcViewSelectImage[pos].findViewById<ProgressBar>(R.id.pBar)
        job = CoroutineScope(Dispatchers.Main).launch {
            pBar.visibility = View.VISIBLE
            val bitmapList = ImageManager.imageResize(listOf(uri))
            pBar.visibility = View.GONE
            adapter.mainArray[pos] = bitmapList[0]
            adapter.notifyItemChanged(pos)
        }

    }
}