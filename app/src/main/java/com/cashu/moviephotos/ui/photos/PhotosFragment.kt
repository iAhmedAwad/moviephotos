/*
 * Copyright (c) 2021.
 * Created by Ahmed Awad
 * ahmed.mmawad@hotmail.com
 */

package com.cashu.moviephotos.ui.photos

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cashu.moviephotos.R
import com.cashu.moviephotos.application.BaseApplication
import com.cashu.moviephotos.data.remote.constants.APIQueries
import com.cashu.moviephotos.utils.ConnectivityUtils
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator

class PhotosFragment : Fragment() {

    private lateinit var viewModel: PhotosViewModel
    private lateinit var recyclerView: RecyclerView
    private var photosAdapter = PhotosAdapter()
    private lateinit var layoutManager: LinearLayoutManager
    private val TAG = "PhotosFragmentTag"
    private lateinit var onScrollListener: RecyclerView.OnScrollListener

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =
            inflater.inflate(R.layout.photos_fragment, container, false)
        prepareView()
        initViews(view)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PhotosViewModel::class.java)

        viewModel.photos.observe(viewLifecycleOwner, {

            photosAdapter.setData(it)
            if (viewModel.page != 1) {
                photosAdapter.removeLoader()
            }
            recyclerView.addOnScrollListener(onScrollListener)

        })

        viewModel.localPhotos.observe(viewLifecycleOwner, Observer {
            photosAdapter.setData(it)
        })

        viewModel.pageCount.observe(viewLifecycleOwner, {
            if (viewModel.page >= it) {
                recyclerView.removeOnScrollListener(onScrollListener)
            }
        })

        viewModel.errorState.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initViews(view: View) {

        recyclerView = view.findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(requireContext())

        onScrollListener = object : RecyclerView.OnScrollListener() {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (layoutManager.findLastVisibleItemPosition() == photosAdapter.itemCount - 8 &&
                    ConnectivityUtils(BaseApplication.appContext).isNetworkConnected
                ) {
                    photosAdapter.addLoader()
                    viewModel.getData()
                }
            }
        }


        recyclerView.itemAnimator = SlideInLeftAnimator()
        recyclerView.itemAnimator?.apply {
            addDuration = 500
            removeDuration = 100
            moveDuration = 1000
            changeDuration = 100
        }
        recyclerView.apply {
            layoutManager = this@PhotosFragment.layoutManager
            setHasFixedSize(true)
            adapter = photosAdapter
        }
        photosAdapter.itemClickedCallBack = {photo->
            val imageUrl = APIQueries.FARM + photo.farm +
                    APIQueries.DOMAIN + photo.server + APIQueries.SLASH +
                    photo.id + APIQueries.UNDERSCORE +
                    photo.secret + APIQueries.EXTENSION
            val action = PhotosFragmentDirections
                .actionPhotosFragmentToSinglePhotoFragment(imageUrl)
            findNavController().navigate(action)

        }

    }

    private fun prepareView() {
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }
}
