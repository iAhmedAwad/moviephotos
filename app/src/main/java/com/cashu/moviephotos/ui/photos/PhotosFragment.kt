/*
 * Copyright (c) 2021.
 * Created by Ahmed Awad
 * ahmed.mmawad@hotmail.com
 */

package com.cashu.moviephotos.ui.photos

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.cashu.moviephotos.R
import com.cashu.moviephotos.application.BaseApplication
import com.cashu.moviephotos.data.remote.constants.APIQueries
import com.cashu.moviephotos.databinding.PhotosFragmentBinding
import com.cashu.moviephotos.utils.ConnectivityUtils
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import java.util.*

class PhotosFragment : Fragment() {

    private lateinit var viewModel: PhotosViewModel
    private lateinit var recyclerView: RecyclerView
    private var photosAdapter = PhotosAdapter()
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var onScrollListener: RecyclerView.OnScrollListener
    private var _binding: PhotosFragmentBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PhotosFragmentBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        prepareView()
        initViews()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PhotosViewModel::class.java)

        ////If there is a network connection, this will start observing
        viewModel.photos.observe(viewLifecycleOwner, {
            photosAdapter.setData(it)
            if (viewModel.page != 1) {
                photosAdapter.removeLoader()
            }
            recyclerView.addOnScrollListener(onScrollListener)

        })

        //If there is no network connection, this will start observing
        viewModel.localPhotos.observe(viewLifecycleOwner, Observer {
            photosAdapter.setData(it)
        })

        viewModel.pageCount.observe(viewLifecycleOwner, {
            if (viewModel.page >= it) {
                recyclerView.removeOnScrollListener(onScrollListener)
            }
        })

        viewModel.errorState.observe(viewLifecycleOwner, {
            showErrorDialog(requireActivity(), it)
        })
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initViews() {

        recyclerView = binding.recyclerView
        layoutManager = LinearLayoutManager(requireContext())

        //Listens on scrolling and invoking paging
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


        //regionRecyclerView animation
        recyclerView.itemAnimator = SlideInLeftAnimator()
        recyclerView.itemAnimator?.apply {
            addDuration = 500
            removeDuration = 100
            moveDuration = 1000
            changeDuration = 100
        }
        //endregion

        recyclerView.apply {
            layoutManager = this@PhotosFragment.layoutManager
            setHasFixedSize(true)
            adapter = photosAdapter
        }

        //Navigating to the single photo fragment.
        photosAdapter.itemClickedCallBack = { photo ->
            if (photo != null) {

                val imageUrl = APIQueries.FARM + photo.farm +
                        APIQueries.DOMAIN + photo.server + APIQueries.SLASH +
                        photo.id + APIQueries.UNDERSCORE +
                        photo.secret + APIQueries.EXTENSION
                val action = PhotosFragmentDirections
                    .actionPhotosFragmentToSinglePhotoFragment(imageUrl)
                findNavController().navigate(action)
            } else {
                val action = PhotosFragmentDirections
                    .actionPhotosFragmentToSinglePhotoFragment(null)
                findNavController().navigate(action)
            }

        }

    }

    /**
     * Making sure that the support action bar is shown, along with clearing the NO_LIMITS flag
     */
    private fun prepareView() {
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Shows an error dialog with a custom message
     */
    private fun showErrorDialog(context: Activity, message: String): Unit {
        val dialog = MaterialDialog(context)
            .title(R.string.error)
            .message(text = message)
            .icon(R.drawable.ic_error)
            .positiveButton(R.string.ok) {
            }
        dialog.show()
    }
}
