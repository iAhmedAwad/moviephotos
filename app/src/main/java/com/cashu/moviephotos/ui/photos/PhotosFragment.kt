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
import android.view.animation.OvershootInterpolator
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cashu.moviephotos.R
import com.cashu.moviephotos.application.BaseApplication
import com.cashu.moviephotos.utils.ConnectivityUtils
import com.cooltechworks.views.shimmer.ShimmerRecyclerView
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class PhotosFragment : Fragment() {

    private lateinit var viewModel: PhotosViewModel
    private lateinit var recyclerView: ShimmerRecyclerView
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
        prepareView()
        initViews(view)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PhotosViewModel::class.java)

        viewModel.photos.observe(viewLifecycleOwner, {

            if (viewModel.page == 1) {
                photosAdapter.setData(it)
            } else {
                photosAdapter.removeLoader()
                photosAdapter.addData(it)
            }

                recyclerView.hideShimmerAdapter()

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
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (layoutManager.findLastVisibleItemPosition() == photosAdapter.itemCount - 8 &&
                    ConnectivityUtils(BaseApplication.appContext).isNetworkConnected
                ) {
                    photosAdapter.addLoader()
                    viewModel.getData()
                }//When there's no connection, is it enough to do nothing?
            }
        }


        recyclerView.addOnScrollListener(onScrollListener)
        recyclerView.itemAnimator = SlideInLeftAnimator()
        //recyclerView.itemAnimator = SlideInUpAnimator(OvershootInterpolator(1f))
        recyclerView.itemAnimator?.apply {
            addDuration = 1000
            removeDuration = 100
            moveDuration = 1000
            changeDuration = 100
        }
        recyclerView.apply {
            layoutManager = this@PhotosFragment.layoutManager
            setHasFixedSize(true)
            adapter = photosAdapter
        }
        recyclerView.showShimmerAdapter()

    }

    private fun prepareView() {
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }
}
