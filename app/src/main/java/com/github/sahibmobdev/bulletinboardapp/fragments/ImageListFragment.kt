package com.github.sahibmobdev.bulletinboardapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.sahibmobdev.bulletinboardapp.R
import com.github.sahibmobdev.bulletinboardapp.databinding.ListImageFragmentBinding
import com.github.sahibmobdev.bulletinboardapp.utils.ItemTouchMoveCallback

class ImageListFragment(private val fragCloseInterface: FragmentCloseInterface, private val newList: ArrayList<String>) : Fragment() {
    val adapter = SelectImageRvAdapter()
    val dragCallback = ItemTouchMoveCallback(adapter)
    val touchHelper = ItemTouchHelper(dragCallback)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_image_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bBack = view.findViewById<Button>(R.id.bBack)
        val rcvView = view.findViewById<RecyclerView>(R.id.rcViewSelectImage)
        touchHelper.attachToRecyclerView(rcvView)
        rcvView.layoutManager = LinearLayoutManager(activity)
        rcvView.adapter = adapter
            val updateList = ArrayList<SelectImageItem>()
            for (n in 0 until newList.size) {
                updateList.add(SelectImageItem(n.toString(), newList[n]))
        }
        adapter.updateAdapter(updateList)
        bBack.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
        }


    }

    override fun onDetach() {
        super.onDetach()
        fragCloseInterface.onClose()
        Log.d("MyLog", "Target pos 0: ${adapter.mainArray[0].title}")
        Log.d("MyLog", "Target pos 1: ${adapter.mainArray[1].title}")
        Log.d("MyLog", "Target pos 2: ${adapter.mainArray[2].title}")
    }
}