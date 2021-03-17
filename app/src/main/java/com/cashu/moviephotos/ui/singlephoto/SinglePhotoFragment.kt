package com.cashu.moviephotos.ui.singlephoto

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cashu.moviephotos.R

class SinglePhotoFragment : Fragment() {

    companion object {
        fun newInstance() = SinglePhotoFragment()
    }

    private lateinit var viewModel: SinglePhotoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.single_photo_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SinglePhotoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
