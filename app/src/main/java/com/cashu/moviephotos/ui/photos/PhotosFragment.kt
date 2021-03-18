package com.cashu.moviephotos.ui.photos

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cashu.moviephotos.R

class PhotosFragment : Fragment() {


    private lateinit var viewModel: PhotosViewModel
    private lateinit var recyclerView: RecyclerView
    private var photosAdapter = PhotosAdapter()
    private  val TAG = "PhotosFragment"

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
        // TODO: Use the ViewModel
        viewModel.photos.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onActivityCreated: ${it[0].id}")
            photosAdapter.setData(it)
        })
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = photosAdapter
        }
    }
/*
    private suspend fun getData() {
        CoroutineScope(IO).launch {

            val response = apiRepository.getDataAsync(
                APIQueries.METHOD_PHOTOS_SEARCH, APIQueries.FORMAT_VALUE, APIQueries.TEXT_VALUE,
                1, 20
            )

            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        //Do something with response e.g show to the UI.
                        val photos = response.body()?.photos?.photo
                        if (photos != null) {
                            photosAdapter.setData(photos)
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Error: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: HttpException) {
                    Toast.makeText(requireContext(), "Exception ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                } catch (e: Throwable) {
                    Toast.makeText(
                        requireContext(),
                        "Ooops: Something else went wrong",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }


 */
}
