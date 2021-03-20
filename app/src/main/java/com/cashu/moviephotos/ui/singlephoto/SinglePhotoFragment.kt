/*
 * Copyright (c) 2021.
 * Created by Ahmed Awad
 * ahmed.mmawad@hotmail.com
 */

package com.cashu.moviephotos.ui.singlephoto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.cashu.moviephotos.R
import com.cashu.moviephotos.databinding.SinglePhotoFragmentBinding

class SinglePhotoFragment : Fragment() {

    private lateinit var viewModel: SinglePhotoViewModel
    private val args: SinglePhotoFragmentArgs by navArgs()
    private lateinit var binding: SinglePhotoFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.single_photo_fragment,
            container,
            false
        )
        prepareView()
        loadImage()
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SinglePhotoViewModel::class.java)

    }

    private fun loadImage() {
        val comingImageUrl = args.imageUrl
        if (comingImageUrl != null) {
            binding.imageUrl = comingImageUrl
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
