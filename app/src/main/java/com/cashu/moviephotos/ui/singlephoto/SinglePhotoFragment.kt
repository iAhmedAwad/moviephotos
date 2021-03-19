/*
 * Copyright (c) 2021.
 * Created by Ahmed Awad
 * ahmed.mmawad@hotmail.com
 */

package com.cashu.moviephotos.ui.singlephoto

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.cashu.moviephotos.R
import com.cashu.moviephotos.utils.ImageUtils

class SinglePhotoFragment : Fragment() {

    private lateinit var viewModel: SinglePhotoViewModel
    private val args: SinglePhotoFragmentArgs by navArgs()
    private lateinit var imageViewSinglePhoto: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.single_photo_fragment, container, false)

        imageViewSinglePhoto = view.findViewById(R.id.iv_single_photo)
        prepareView()
        loadImage()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SinglePhotoViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun loadImage() {
        val imageUrl = args.imageUrl
        Log.d(TAG, "onCreateView: imageUrl is: $imageUrl")
        if (imageUrl != null) {
            ImageUtils.setImageView(requireContext(), imageUrl, imageViewSinglePhoto)
        }
    }

    private fun prepareView() {
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    companion object {
        private const val TAG = "SinglePhotoFragment"
    }

}
