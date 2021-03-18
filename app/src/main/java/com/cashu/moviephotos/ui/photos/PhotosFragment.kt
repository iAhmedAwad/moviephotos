package com.cashu.moviephotos.ui.photos

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cashu.moviephotos.R

class PhotosFragment : Fragment() {


    private lateinit var viewModel: PhotosViewModel
    private lateinit var recyclerView: RecyclerView
    private var photosAdapter = PhotosAdapter()
    private lateinit var layoutManager: LinearLayoutManager
    private val TAG = "PhotosFragment"
    private lateinit var onScrollListener: RecyclerView.OnScrollListener

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =
            inflater.inflate(R.layout.photos_fragment, container, false)

        initViews(view)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PhotosViewModel::class.java)

        viewModel.photos.observe(viewLifecycleOwner, {
            Log.d(TAG, "onActivityCreated: ${it[0].id}")
            if (viewModel.page == 1) {
                photosAdapter.setData(it)
            } else {
                photosAdapter.removeLoader()
                photosAdapter.addData(it)
            }
        })

        viewModel.pageCount.observe(viewLifecycleOwner, {
            if (viewModel.page >= it) {
                recyclerView.removeOnScrollListener(onScrollListener)
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(requireContext())

        onScrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (layoutManager.findLastVisibleItemPosition() == photosAdapter.itemCount - 10) {
                    photosAdapter.addLoader()
                    viewModel.getData()
                }
            }
        }


        recyclerView.addOnScrollListener(onScrollListener)
        recyclerView.apply {
            layoutManager = this@PhotosFragment.layoutManager
            setHasFixedSize(true)
            adapter = photosAdapter
        }
    }

}
